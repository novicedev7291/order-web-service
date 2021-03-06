package com.coding.saga.web.config;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Component
public class GoogleAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(GoogleAuthenticationHandler.class);
    private final CustomerService customerService;

    public GoogleAuthenticationHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.debug("Authentication successful, user object {}", authentication);

        if(authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof OAuth2User oAuth2User) {

            String email = oAuth2User.getAttribute("email");

            log.debug("OAuth user email is {}", email);


            Optional<Customer> possibleCustomer = customerService.findByEmailId(email);
            if(possibleCustomer.isPresent()) {
                Customer aCustomer = possibleCustomer.get();
                request.getSession().setAttribute("customerId", aCustomer.id().value());
                response.sendRedirect("/");
            }else {
                response.sendRedirect("/registration");
            }

        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
