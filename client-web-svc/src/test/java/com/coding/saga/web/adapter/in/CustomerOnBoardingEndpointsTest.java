package com.coding.saga.web.adapter.in;

import com.coding.saga.web.config.GoogleAuthenticationHandler;
import com.coding.saga.web.config.OAuth2UserFactory;
import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@WebMvcTest
@Import(GoogleAuthenticationHandler.class)
class CustomerOnBoardingEndpointsTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;


    @Test
    void shouldLoadRegisterPageWhenEmailNotFound() throws Exception {
        mvc.perform(get("/registration").with(oAuth2User("ky@abc.com", "Sandeep Yadav")))
                .andExpect(status().isOk());

        verifyNoInteractions(service);
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

        verify(service, times(1)).create(any(Customer.class));
    }

    private SecurityMockMvcRequestPostProcessors.OAuth2LoginRequestPostProcessor oAuth2User(String email, String name) {
        return SecurityMockMvcRequestPostProcessors
                .oauth2Login()
                .oauth2User(OAuth2UserFactory.createOAuth2User(email, name));
    }
}
