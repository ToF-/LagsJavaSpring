package com.example.lags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.SQLException;
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

    @GetMapping("customerCreate")
    public String getCustomerEdit(Model model) {
        CustomerForm customerForm = new CustomerForm();
        model.addAttribute("customerForm",customerForm);
        return "customerCreate";
    }
    @PostMapping("customerCreate")
    public String checkCustomerInfo(Model model, @Valid CustomerForm customerForm, BindingResult bindingResult) throws SQLException {
        if(bindingResult.hasErrors()) {
            return "customerCreate";
        }
        if(!customerRepository.create(customerForm.getCustomer())) {
            ObjectError error = new ObjectError("error","This customer already exists.");
            String message = String.format("The customer %s already exists.", customerForm.getId());
            model.addAttribute("errorMsg",message);
            bindingResult.addError(error);
            return "customerCreate";
        }
        return "redirect:/customers";
    }
}
