package com.example.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {

    @GetMapping("/hi")
    @ResponseBody
    public String index() {
        return "hello";
    }
}