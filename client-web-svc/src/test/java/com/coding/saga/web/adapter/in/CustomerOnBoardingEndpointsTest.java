package com.coding.saga.web.adapter.in;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.CustomerService;
import com.coding.saga.web.domain.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@WebMvcTest
class CustomerOnBoardingEndpointsTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @Test
    void shouldRedirectToIndexPageWhenCustomerAlreadyExists() throws Exception {
        String email = "ky@xyz.com";
        Customer aCustomer = new Customer(
                CustomerId.newId(45),
                new Name("Kuldeep", "Yadav"),
                email
        );
        when(service.findByEmailId(email))
                .thenReturn(Optional.of(aCustomer));

        mvc.perform(get("/customerRegistration").with(oAuth2User("ky@xyz.com", "Kuldeep Yadav")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldLoadRegisterPageWhenEmailNotFound() throws Exception {
        String email = "ky@abc.com";
        when(service.findByEmailId(email))
                .thenReturn(Optional.empty());

        mvc.perform(get("/customerRegistration").with(oAuth2User("ky@abc.com", "Sandeep Yadav")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectToIndexPageOnRegisterEndpoint() throws Exception{
        mvc.perform(
                post("/register")
                   .param("firstname", "Kuldeep")
                   .param("lastname", "Yadav")
                   .param("email", "email")
                   .with(oAuth2User("ky@abc.com", "Raman Bhaniot"))
                   .with(csrf())
           )
           .andExpect(status().is3xxRedirection());
    }

    private SecurityMockMvcRequestPostProcessors.OAuth2LoginRequestPostProcessor oAuth2User(String email, String name) {
        return SecurityMockMvcRequestPostProcessors
                .oauth2Login()
                .oauth2User(OAuth2UserFactory.createOAuth2User(email, name));
    }
}
