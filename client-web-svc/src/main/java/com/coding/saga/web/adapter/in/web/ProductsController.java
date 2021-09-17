package com.coding.saga.web.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Controller
@RequestMapping("/")
public class ProductsController {

    @GetMapping
    public String productsView(Model model) {
        return "index";
    }
}
