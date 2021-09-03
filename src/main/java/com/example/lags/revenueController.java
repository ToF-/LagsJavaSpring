package com.example.lags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

@Controller
public class revenueController {

    @Autowired
    private JdbcRepository jdbcRepository;
    @GetMapping("/revenue")
    public String getRevenue(Model model) {
        List <Order> orders = new ArrayList<>();
        RevenueForm revenueForm = new RevenueForm(LocalDate.now(), LocalDate.now().plusMonths(1), orders);
        model.addAttribute("revenueForm", revenueForm);
        return "/revenue";
    }

    @GetMapping("/revenue/{start}/{end}")
    public String getRevenueFor(@PathVariable("start") String sStart, @PathVariable("end") String sEnd, Model model) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(sStart, dateTimeFormatter);
        LocalDate end = LocalDate.parse(sEnd, dateTimeFormatter);
        List<Order> orders = jdbcRepository.findOrders(start, end);
        RevenueForm revenueForm = new RevenueForm(start, end, orders);
        revenueForm.computeRevenue();
        System.out.println("***** parsed arguments *****");
        model.addAttribute("revenueForm", revenueForm);
        return "/revenue";
    }
    @PostMapping("/revenue")
    public String postRevenue(Model model, @Valid RevenueForm revenueForm, BindingResult bindingResult) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return String.format("redirect:/revenue/%s/%s",
                revenueForm.getStart().format(dateTimeFormatter),
                revenueForm.getEnd().format(dateTimeFormatter));
    }
}
