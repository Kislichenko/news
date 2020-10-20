package com.kislichenko.news.errors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.kislichenko.news.exceptions.ClientNotFoundException;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice //используется, чтобы один ExceptionHandler можно было применить к нескольким контроллерам
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    //handler для отлова невалидных входных аргументов в контроллерах
//    @Override
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//
//        Map<String, Set<String>> errorsMap = fieldErrors.stream().collect(
//                Collectors.groupingBy(FieldError::getField,
//                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
//                )
//        );
//
//        ApiError apiError = new ApiError(status);
//        apiError.setError("ARGUMENT_NOT_VALID");
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//            apiError.setMessage(errorsMap.toString());
//
//
//        return new ResponseEntity(apiError, headers, status);
//    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> processJWTVerificationException(JWTVerificationException ex) {
        logger.debug("JWT is not verificated!");

        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setError("INVALID_JWT");
        apiError.setMessage("JWT token is not valid");

        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler(JDBCException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<Object> processJDBCException(JDBCException ex) {
//        logger.debug("processJDBCException is catched!");
//
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
//        apiError.setError(getErrorFromJDBCException(ex.getSQLException().getMessage()));
//        apiError.setMessage(getErrorFromJDBCException(ex.getSQLException().getMessage()));
//
//        return buildResponseEntity(apiError);
//    }
//
//    @ExceptionHandler(ClientNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Object> processClientNotFoundException(ClientNotFoundException ex) {
//        logger.debug("ClientNotFoundException is catched!");
//
//        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
//        apiError.setError("NOT_FOUND");
//        apiError.setMessage(ex.getMessage());
//
//        return buildResponseEntity(apiError);
//    }

    private String getErrorFromJDBCException(String message) {
        String tmp = "";
        if (message.contains("already exists")) {
            tmp = message.substring(message.lastIndexOf("Key (") + "Key (".length());
            tmp = tmp.substring(0, tmp.indexOf(")"));
            return tmp.toUpperCase() + "_ALREADY_USED";
        }
        return "";
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
