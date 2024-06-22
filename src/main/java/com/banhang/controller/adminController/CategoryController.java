package com.banhang.controller.adminController;

import com.banhang.entity.Categories;
import com.banhang.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/category")
    public String index(Model model, @ModelAttribute("category") Categories category) {
        model.addAttribute("category",
                categoryRepository.findAll());
        return "admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }

    @PostMapping("/category/save")
    public String createCategory(@ModelAttribute("category") Categories category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

}
