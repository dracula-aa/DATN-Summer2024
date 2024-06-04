package com.banhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/product")
    public String index()
    {
        return "/product/product";
    }

    @GetMapping("/product/detail")
    public String show(Model model)
    {
        return "/product/product-detail";
    }

}
