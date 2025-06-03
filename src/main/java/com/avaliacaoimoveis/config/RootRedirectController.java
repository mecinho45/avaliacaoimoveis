package com.avaliacaoimoveis.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {

    @GetMapping("/")
    public String redirectRoot() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "index"; // Se autenticado, mostra a página index
        }
        return "redirect:/login";
    }

    @GetMapping("/index.html")
    public String redirectIndex() {
        return "index"; // Sempre mostra a página index, Spring Security bloqueia se não autenticado
    }
    
    @GetMapping("/index")
    public String showIndex() {
        return "index"; // Endpoint direto para a página index
    }
}
