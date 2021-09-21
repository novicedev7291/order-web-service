package com.coding.saga.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final GoogleAuthenticationHandler googleAuthenticationHandler;

    public WebSecurityConfig(GoogleAuthenticationHandler authenticationHandler) {
        super();
        this.googleAuthenticationHandler = authenticationHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .mvcMatchers("/", "/error", "/login", "/**/*.css", "/**/*.js")
                        .permitAll()
                    .mvcMatchers("/**")
                        .authenticated()
                    .mvcMatchers(HttpMethod.POST, "/logout")
                        .authenticated()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                        .permitAll()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                    .successHandler(googleAuthenticationHandler);
//                    .defaultSuccessUrl("/customerRegistration");
    }
}
