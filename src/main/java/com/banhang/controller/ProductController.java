package com.banhang.controller;

import com.banhang.entity.Products;
import com.banhang.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/product")
    public String index(Model model) {
        List<Products> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product/product";
    }

    @GetMapping("/product/{slug}")
    public String show(Model model, @PathVariable("slug") String slug) {
        System.out.println(slug);
        Optional<Products> product = productRepository.findBySlug(slug);
        if (product.isPresent()) {
            model.addAttribute("product", product.orElse(null));
        } else {
            model.addAttribute("message", "Sản phẩm bạn tìm không tồn tại hoặc đã bị xoá");
        }
        return "product/product-detail";
    }

    @GetMapping("/filter/{type}")
    public String filterProduct(Model model, @PathVariable("type") String type) {
        List<Products> products = productRepository.filterProduct(type);
        if (products != null) {
            model.addAttribute("products", products);
        } else {
            model.addAttribute("message", "Không tìm thấy sản phẩm bạn cần");
        }
        return "product/product";
    }

}
