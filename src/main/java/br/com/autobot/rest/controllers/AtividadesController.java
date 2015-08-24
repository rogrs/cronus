package br.com.autobot.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AtividadesController {

    @RequestMapping("/atividade")
    public String home() {
        return "atividade";
    }

}
