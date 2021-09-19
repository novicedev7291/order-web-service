package com.coding.saga.web.adapter.in.web;

import com.coding.saga.web.domain.Customer;
import com.coding.saga.web.domain.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Controller
@RequestMapping("/register")
class RegisterCompletionController {
    private static final Logger log = LoggerFactory.getLogger(RegisterCompletionController.class);
    private final CustomerService customerService;

    RegisterCompletionController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String completionView(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        log.debug("user attributes are : {}", oAuth2User.getAttributes());

        log.debug("checking if user already exist");
        String email = oAuth2User.getAttribute("email");

        Optional<Customer> possibleCustomer = customerService.findByEmailId(email);
        if(possibleCustomer.isPresent()) {
            return "redirect:index";
        }

        log.debug("new user with email Id {}", email);

        model.addAttribute("email", email);
        return "register";
    }
}
