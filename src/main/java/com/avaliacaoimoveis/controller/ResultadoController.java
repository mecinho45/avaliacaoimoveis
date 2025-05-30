package com.avaliacaoimoveis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultadoController {
    @GetMapping(value = {"/resultado", "/resultado.html"})
    public String mostrarResultado(@RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        return "resultado";
    }
}
