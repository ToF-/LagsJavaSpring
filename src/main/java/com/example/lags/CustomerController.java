package com.example.lags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("customers")
    public String getCustomers(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customerList", customers);
        return "customers";
    }
}
