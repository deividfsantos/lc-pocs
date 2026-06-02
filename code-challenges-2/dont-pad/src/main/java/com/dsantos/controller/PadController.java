package com.dsantos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PadController {

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/{padId:[a-zA-Z0-9_-]+}")
    public String getPad(@PathVariable String padId) {
        return "forward:/index.html";
    }
}
