package com.coding.saga.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Component
public class GoogleAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(GoogleAuthenticationHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.debug("Authentication successful, user object {}", authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
