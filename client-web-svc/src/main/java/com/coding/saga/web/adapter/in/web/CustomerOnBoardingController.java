package com.coding.saga.web.adapter.in.web;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerService;
import com.coding.saga.web.domain.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Controller
public
class CustomerOnBoardingController {
    private static final Logger log = LoggerFactory.getLogger(CustomerOnBoardingController.class);
    private final CustomerService customerService;

    public CustomerOnBoardingController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/registration")
    public String completionView(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        String name = oAuth2User.getAttribute("name");

        log.debug("Found {} authenticated user with {} email", name, email);

        CustomerRegistrationForm registrationForm = new CustomerRegistrationForm(email);
        if(Objects.nonNull(name)) {
            int spaceIdx = name.indexOf(' ');
            if(spaceIdx == -1) {
                log.debug("only single name found : {}", name);
                registrationForm.setFirstname(name);
            }else {
                String firstname = name.substring(0, spaceIdx);
                String lastname = name.substring(spaceIdx + 1);

                registrationForm.setFirstname(firstname);
                registrationForm.setLastname(lastname);
            }
        }

        model.addAttribute("registration", registrationForm);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerCustomer(Model model, CustomerRegistrationForm customerRegistrationForm) {
        Customer aCustomer = new Customer(
                null,
                new Name(
                        customerRegistrationForm.getFirstname(),
                        customerRegistrationForm.getLastname()
                ),
                customerRegistrationForm.getEmail()
        );
        customerService.create(aCustomer);

        model.addAttribute("isAuthenticated", true);
        return "redirect:/";
    }
}
