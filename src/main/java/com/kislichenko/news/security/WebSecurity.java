package com.kislichenko.news.security;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.errors.FilterChainExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.kislichenko.news.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserRepository appUserRepository;
    private FilterChainExceptionHandler filterChainExceptionHandler;

    public WebSecurity(UserDetailsServiceImpl userDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AppUserRepository appUserRepository,
                       FilterChainExceptionHandler filterChainExceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserRepository = appUserRepository;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager());
        jwtAuthorizationFilter.setAppUserRepository(appUserRepository);
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(), appUserRepository);

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                //.antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/secret/**").hasAnyRole("ADMIN", "ADMIN1")
                //.antMatchers("/change-role").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(filterChainExceptionHandler, jwtAuthenticationFilter.getClass())
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(filterChainExceptionHandler, jwtAuthorizationFilter.getClass())
                .addFilter(jwtAuthorizationFilter)
                //отключение сессий в Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();


        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        return source;
    }
}
