package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @GetMapping("/") // это юрл по которому ты заходишь на страницу
    public String index() {
        logger.info("index page was open!");
        return "index"; // а это название html файла, который будет показан при обращении к юрл выше
        // типо кагда ты пишешь localhost:8080/ -> открывается index.html
        // все html файлы хранятся в templates
    }

    @GetMapping("/mult")
    public String multiply() {
        logger.info("mult page was open!");
        return "mult";
    }

    @GetMapping("/multres")
    public String multiplyRes() {
        logger.info("multres page was open!");

        return "multres";
    }

    @GetMapping("/uploadFile")
    public String uploadFile() {
        logger.info("upload page was open!");

        return "uploadMedia";
    }
}