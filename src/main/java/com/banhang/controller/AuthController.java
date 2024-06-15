package com.banhang.controller;

import com.banhang.entity.Users;
import com.banhang.repository.UserRepository;
import com.banhang.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String checkLogin(Model model, @RequestParam(name = "remember", required = false) String remember,
                             @RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        Users user = userRepository.findByEmail(username);
        try {
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    if (user.getRole()) {
                        sessionService.setAttribute("user", user);
                        return "redirect:/home";
                    } else {
                        sessionService.setAttribute("user", user);
                        return "redirect:/home";
                    }
                } else {
                    model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
                }
            } else {
                model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            }

        } catch (Exception e) {
            System.out.print(e);
            model.addAttribute("message", "Lỗi không xác định, xin thử lại!");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model, @ModelAttribute("user") Users user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(Model model, @ModelAttribute("user") @Validated Users user,
                                 BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return "auth/register";
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                model.addAttribute("message", "Đăng ký không thành công, email đã tồn tại trong hệ thống.");
                return "auth/register";
            }

            userRepository.save(user);
            model.addAttribute("message", "Đăng ký thành công");
            sessionService.setAttribute("user", user);
            return "redirect:/home";
        } catch (Exception ex) {
            model.addAttribute("message", "Đăng ký không thành công, vui lòng thử lại sau.");
            ex.printStackTrace();
            return "auth/register";
        }
    }
    @GetMapping("/logout")
    public String logout() {
        sessionService.invalidate();
        return "redirect:/login";
    }

}
