package br.com.autobot.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PluginsController {

    @RequestMapping("/plugin")
    public String home() {
        return "plugin";
    }

}
