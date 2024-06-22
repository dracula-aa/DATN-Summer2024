package com.banhang.controller;

import com.banhang.entity.Customers;
import com.banhang.entity.Orders;
import com.banhang.repository.CustomerRepository;
import com.banhang.repository.OrderDetailRepository;
import com.banhang.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public String index(Model model, @ModelAttribute("customer") Customers customer) {
        return "checkout/checkout";
    }

    @PostMapping()
    public String saveCustomerInfo(Model model, @ModelAttribute("customer") Customers customer,
                                   @ModelAttribute("order") Orders order) {
        customerRepository.save(customer);
        model.addAttribute("message", "Thanh toán thành công");

//        orderRepository.save
        return "checkout/checkout";
    }
}
