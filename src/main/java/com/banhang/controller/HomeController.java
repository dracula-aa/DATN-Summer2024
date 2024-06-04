package com.banhang.controller;

import com.banhang.entity.Products;
import com.banhang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping
    public String index(Model model) {
        List<Products> products = productRepository.findTopProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
