package br.com.autobot.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlanController {

    @RequestMapping("/plan")
    public String home() {
        return "plan";
    }

}