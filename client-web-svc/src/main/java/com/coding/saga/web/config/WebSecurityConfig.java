package com.coding.saga.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .mvcMatchers("/", "/error", "/login")
                        .permitAll()
                    .mvcMatchers("/**")
                    .authenticated()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

                        }
                    })
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                        .permitAll()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userAuthoritiesMapper(new GrantedAuthoritiesMapper() {
                            @Override
                            public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> collection) {
                                return null;
                            }
                        });
    }
}
