package com.kislichenko.news.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Role;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.kislichenko.news.security.SecurityConstants.*;

//авторизация - проверка прав доступа залогиненного пользователя
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Setter
    private AppUserRepository appUserRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        //проверка того, что извлеченный заголовок не пустой и имеет префих Bearer
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            //вызов следующего фильтра в цепочке
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //запускаем следующий фильтр в цепочке фильтров
        chain.doFilter(req, res);

    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            //верификаця полученного в заголовке токена без префикса
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            System.out.println("AUTH: "+user);

            if (user != null) {

                AppUser appUser = appUserRepository.findByUsername(user);

                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

                if (appUser != null && appUser.getRoles() != null) {
                    for (Role role : appUser.getRoles()) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
                    }
                }

                //внутренний токен Spring Security
                return new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        grantedAuthorities);
            }
            return null;
        }
        return null;
    }
}
