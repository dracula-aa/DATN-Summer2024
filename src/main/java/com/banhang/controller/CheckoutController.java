package com.banhang.controller;

import com.banhang.entity.Customers;
import com.banhang.entity.Orders;
import com.banhang.repository.CustomerRepository;
import com.banhang.repository.OrderDetailRepository;
import com.banhang.repository.OrderRepository;
import com.banhang.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private VNPayService vnPayService;

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

        return "checkout/checkout";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus == 1){

        }
        return paymentStatus == 1 ? "/checkout/orderSuccess" : "/checkout/orderFailed";
    }
}
