package com.coding.saga.web.config;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.CustomerService;
import com.coding.saga.web.domain.InMemoryCustomerRepository;
import com.coding.saga.web.domain.Name;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class GoogleAuthenticationHandlerTest {

    @Test
    void requestShouldForwardToRegisterPageWhenEmailNotExist() throws ServletException, IOException {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        GoogleAuthenticationHandler handler = new GoogleAuthenticationHandler(new CustomerService(repository));

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(
                OAuth2UserFactory.createOAuth2User("ky@xyz.com", "Kuldeep Yadav"),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                "testClientRegistrationId"
        );

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(request.getSession().getAttribute("customerId"))
                .isNull();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.FOUND.value());
    }

    @Test
    void requestShouldForwardToIndexPageWhenEmailExistWithCustomerIdInSession() throws ServletException, IOException {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(new Customer(CustomerId.newId(234), new Name("Kuldeep", "Yadav"), "ky@xyz.com"));

        GoogleAuthenticationHandler handler = new GoogleAuthenticationHandler(new CustomerService(repository));

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Authentication authentication = new OAuth2AuthenticationToken(
                OAuth2UserFactory.createOAuth2User("ky@xyz.com", "Kuldeep Yadav"),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                "testClientRegistrationId"
        );

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(request.getSession().getAttribute("customerId"))
                .isNotNull().isEqualTo(234L);
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.FOUND.value());
    }

}