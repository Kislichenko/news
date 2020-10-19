package com.kislichenko.news.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Role;
import com.kislichenko.news.errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kislichenko.news.security.SecurityConstants.*;

//аутентификация - проверка логин/пароль
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AppUserRepository appUserRepository) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

        try {
            //из json запроса вытаскиваем данные (логин, пароль) о пользователе
            AppUser credential = new ObjectMapper()
                    .readValue(req.getInputStream(), AppUser.class);

            System.out.println(credential.getUsername());
            AppUser appUser = appUserRepository.findByUsername(credential.getUsername());

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            if (appUser != null && appUser.getRoles() != null) {
                for (Role role : appUser.getRoles()) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                }
            }

            //производим аутиентификацию. Чтобы произвести аутентификацию Spring Security
            //использует метод loadUserByUsername из сервиса UserDetailsServiceImpl,
            //который имплементит UserDetailService
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getUsername(),
                            credential.getPassword(),
                            grantedAuthorities)
            );
        } catch (IOException e) {
            System.out.println("TTTT2");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        System.out.println(failed.getMessage());

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setError(failed.getMessage().toUpperCase().replaceAll(" ", "_"));
        apiError.setMessage(failed.getMessage());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(ow.writeValueAsString(apiError));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        AppUser appUser = appUserRepository.findByUsername(((User) auth.getPrincipal()).getUsername());

        Set<String> grantedAuthorities = new HashSet<>();

        if (appUser != null && appUser.getRoles() != null) {
            for (Role role : appUser.getRoles()) {
                grantedAuthorities.add(role.getName());
            }
        }

        //Создаем токен. Subject - имя пользователя. Механизм шифрования - HMAC512.
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withArrayClaim("roles", grantedAuthorities.stream().toArray(String[]::new))
                .sign(HMAC512(SECRET.getBytes()));

        //в заголовок ответа добавляем сгенерированный JWT токен
        //TOKEN_PREFIX должен быть Bearer, чтобы дальше токе проверялся.
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
