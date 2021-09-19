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
import java.util.Optional;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Controller
class CustomerOnBoardingController {
    private static final Logger log = LoggerFactory.getLogger(CustomerOnBoardingController.class);
    private final CustomerService customerService;

    CustomerOnBoardingController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerRegistration")
    public String completionView(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        log.debug("user with email Id {}", email);

        String name = oAuth2User.getAttribute("name");
        log.debug("user with {} name", name);

        Optional<Customer> possibleCustomer = customerService.findByEmailId(email);
        if(possibleCustomer.isPresent()) {
            return "redirect:index";
        }

        CustomerRegistrationForm registrationForm = new CustomerRegistrationForm(email);
        if(Objects.nonNull(name)) {
            Name realName;
            int spaceIdx = name.indexOf(' ');
            if(spaceIdx == -1) {
                log.debug("only single name found : {}", name);
                registrationForm.setFirstname(name);
                realName = new Name(name, "");
            }else {
                String firstname = name.substring(0, spaceIdx);
                String lastname = name.substring(spaceIdx + 1);

                registrationForm.setFirstname(firstname);
                registrationForm.setLastname(lastname);

                realName = new Name(firstname, lastname);
            }

            Customer newCustomer = new Customer(null, realName, email);
            log.debug("new customer details : {}", newCustomer);
        }

        model.addAttribute("registration", registrationForm);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerCustomer(CustomerRegistrationForm customerRegistrationForm) {
        Customer aCustomer = new Customer(
                null,
                new Name(
                        customerRegistrationForm.getFirstname(),
                        customerRegistrationForm.getLastname()
                ),
                customerRegistrationForm.getEmail()
        );
        customerService.create(aCustomer);

        return "redirect:/";
    }
}
