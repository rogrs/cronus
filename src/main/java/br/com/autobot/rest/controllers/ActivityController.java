package br.com.autobot.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActivityController {

    @RequestMapping("/activity")
    public String home() {
        return "activity";
    }

}
