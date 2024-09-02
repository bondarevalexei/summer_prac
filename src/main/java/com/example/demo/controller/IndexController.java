package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class IndexController {
    private static final Logger logger = LogManager.getLogger(IndexController.class);


    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null) {
            String username = authentication.getName();
            Optional<User> optionalUser = userService.findByUsername(username);


            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<Role> roles = new ArrayList<>(user.getRoles());
                if (!roles.isEmpty() && roles.get(0).getName().equals("ROLE_ADMIN")) {
                    logger.info("Admin accessed the order list page.");
                    return "redirect:/orderList";
                } else {
                    logger.info("User accessed the index page.");
                    List<Service> services = serviceRepository.findAll();
                    model.addAttribute("services", services);
                    return "redirect:/orderForm";
                }
            } else {
                logger.warn("User not found in the database.");
                return "redirect:/login";
            }
        } else {
            logger.info("User accessed the index page.");
            List<Service> services = serviceRepository.findAll();
            model.addAttribute("services", services);
            return "redirect:/orderForm";
        }
    }
    @GetMapping("/orderList")
    public String orderList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page, 20, Sort.by("dateTime").descending()));
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @PostMapping("/confirmOrder/{id}")
    public String confirmOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        }
        return "redirect:/orderList";
    }

    @PostMapping("/completeOrder/{id}")
    public String completeOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }
        return "redirect:/orderList";
    }

    @PostMapping("/rejectOrder/{id}")
    public String rejectOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
        }
        return "redirect:/orderList";
    }

    @GetMapping("/scheduleOrder/{id}")
    public String showScheduleOrderForm(@PathVariable Long id, Model model) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            model.addAttribute("order", order);
            return "scheduleOrder";
        }
        return "redirect:/orderList";
    }

    @PostMapping("/scheduleOrder/{id}")
    public String scheduleOrder(@PathVariable Long id, @RequestParam LocalDateTime scheduledTime) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setDateTime(scheduledTime);
            order.setStatus(OrderStatus.SCHEDULED);
            orderRepository.save(order);
        }
        return "redirect:/orderList";
    }

    @GetMapping("/redir")
    public String redirectService(Model model){
        return "redirect:/";
    }
}