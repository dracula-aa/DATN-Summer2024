package com.banhang.controller;

import com.banhang.entity.Categories;
import com.banhang.entity.Products;
import com.banhang.repository.CategoryRepository;
import com.banhang.repository.ProductRepository;
import com.banhang.service.SessionService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SessionService sessionService;

    @GetMapping("/product")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "9") int size) {
        int start = page * size;

        Page<Products> productsPage = productRepository.findAll(PageRequest.of(page, size));
        List<Products> products = productsPage.getContent();

        List<Categories> categories = categoryRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", productsPage.getTotalPages());

        sessionService.setAttribute("productsCount", productRepository.count());

        return "product/product";
    }

    @GetMapping("/product/{slug}")
    public String show(Model model, @PathVariable("slug") String slug) {
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
            List<Categories> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
        } else {
            model.addAttribute("message", "Không tìm thấy sản phẩm bạn cần");
        }
        return "product/product";
    }

    @GetMapping("/product/search")
    public String search(@RequestParam(name = "keyword", required = false) String keywords, Model model) {
        String searchKeywords = "%" + keywords + "%";
        List<Products> products = productRepository.findByKeywords(searchKeywords);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keywords);

        return "product/product";
    }

    @GetMapping("/product/category/{slug}")
    public String findByCategory(@PathVariable(name = "slug", required = false) String slug, Model model) {
        List<Products> products = productRepository.findAllByCategorySlug(slug);
        List<Categories> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "product/product";
    }
}
