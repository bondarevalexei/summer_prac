package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        System.out.println(status);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                return "error-404";
            } else if (statusCode == 500) {
                return "error-500";
            }
        }
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}