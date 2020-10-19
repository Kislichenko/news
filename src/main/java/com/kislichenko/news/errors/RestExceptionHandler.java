package com.kislichenko.news.errors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.GenericJDBCException;
import org.postgresql.util.PSQLException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice //используется, чтобы один ExceptionHandler можно было применить к нескольким контроллерам
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

   // handler для отлова ошибок типа Exception в контроллерах
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ResponseEntity<Object> handleException(
//            Exception ex) {
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }

    //handler для отлова невалидных входных аргументов в контроллерах
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );
        return new ResponseEntity(errorsMap.isEmpty() ? ex : errorsMap, headers, status);
    }

    @ExceptionHandler( BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> processAuthenticationException( BadCredentialsException ex) {
        System.out.println(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setError("INVALID_JWT");
        apiError.setMessage("JWT токен невалидный");

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> processRuntimeException(JWTVerificationException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setError("INVALID_JWT");
        apiError.setMessage("JWT токен невалидный");

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> processJDBCException(JDBCException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);

        apiError.setError(getErrorFromJDBCException(ex.getSQLException().getMessage()));
        apiError.setMessage(ex.getSQLException().getMessage());

        return buildResponseEntity(apiError);
    }

    private String getErrorFromJDBCException(String message){
        String tmp = "";
        if(message.contains("already exists")){
            tmp = message.substring(message.lastIndexOf("Key (")+"Key (".length());
            tmp = tmp.substring(0, tmp.indexOf(")"));
            return tmp.toUpperCase()+"_ALREADY_USED";
        }
        return "";
    }

    //handler для отлова ошибок типа RuntimeException в контроллерах
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<Object> processRuntimeException(RuntimeException ex) {
//        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
