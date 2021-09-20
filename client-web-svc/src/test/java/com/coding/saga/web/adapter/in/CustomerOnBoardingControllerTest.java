package com.coding.saga.web.adapter.in;

import com.coding.saga.web.adapter.in.web.CustomerOnBoardingController;
import com.coding.saga.web.adapter.in.web.CustomerRegistrationForm;
import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerId;
import com.coding.saga.web.domain.CustomerService;
import com.coding.saga.web.domain.InMemoryCustomerRepository;
import com.coding.saga.web.domain.Name;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerOnBoardingControllerTest {

    @Test
    void shouldLoadCustomerRegistrationFormWhenEmailNotFound() {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);


        Model model = new ConcurrentModel();
        String email = "ky@abc.com";
        DefaultOAuth2User oAuth2User = OAuth2UserFactory.createOAuth2User(email, "Kuldeep Yadav");

        CustomerOnBoardingController controller = new CustomerOnBoardingController(service);
        controller.completionView(model, oAuth2User);

        assertThat(model.getAttribute("registration"))
                .hasFieldOrPropertyWithValue("email", email);

    }

    @Test
    void shouldNotLoadCustomerRegistrationFormWhenEmailExists() {
        String email = "john.oliver@awesomeblah.com";
        Customer aCustomer = new Customer(
                CustomerId.newId(89),
                new Name("John", "Oliver"),
                email
        );
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(aCustomer);

        CustomerService service = new CustomerService(repository);

        Model model = new ConcurrentModel();
        OAuth2User oAuth2User = OAuth2UserFactory.createOAuth2User(email, "Kuldeep");

        CustomerOnBoardingController controller = new CustomerOnBoardingController(service);

        controller.completionView(model, oAuth2User);


        assertThat(model.getAttribute("isAuthenticated"))
                .isInstanceOf(Boolean.class);

        if(model.getAttribute("isAuthenticated") instanceof Boolean value) {
            assertThat(value).isTrue();
        }
    }

    @Test
    void shouldHaveCustomerPropertyAfterSuccessfulRegistration() {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);

        CustomerRegistrationForm form = new CustomerRegistrationForm();
        form.setFirstname("Kuldeep");
        form.setLastname("Yadav");
        form.setEmail("ky@xyz.com");

        Model model = new ConcurrentModel();

        CustomerOnBoardingController controller = new CustomerOnBoardingController(service);
        controller.registerCustomer(model, form);

        Object isAuthenticated = model.getAttribute("isAuthenticated");
        assertThat(isAuthenticated)
                .isInstanceOf(Boolean.class);
        if(isAuthenticated instanceof Boolean value) {
            assertThat(value).isTrue();
        }
    }
}
