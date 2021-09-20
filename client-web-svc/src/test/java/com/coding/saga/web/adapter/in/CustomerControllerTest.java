package com.coding.saga.web.adapter.in;

import com.coding.saga.web.adapter.in.web.CustomerOnBoardingController;
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

import java.util.Map;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
class CustomerControllerTest {

    @Test
    void shouldLoadCustomerRegistrationFormWhenEmailNotFound() {
        InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);


        Model model = new ConcurrentModel();
        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(emptyList(),
                Map.of(
                        "email", "ky@abc.com",
                        "name", "Kuldeep Yadav"
                ),
                "name"
        );

        CustomerOnBoardingController controller = new CustomerOnBoardingController(service);
        controller.completionView(model, oAuth2User);

        assertThat(model.getAttribute("registration"))
                .hasFieldOrPropertyWithValue("email", "ky@abc.com");

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
        OAuth2User oAuth2User = new DefaultOAuth2User(
                emptyList(),
                Map.of("email", email, "name", "Kuldeep"),
                "name"
        );

        CustomerOnBoardingController controller = new CustomerOnBoardingController(service);

        controller.completionView(model, oAuth2User);

        assertThat(model)
                .hasAllNullFieldsOrProperties();
    }
}
