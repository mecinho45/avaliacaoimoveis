package com.avaliacaoimoveis.controller;

import com.avaliacaoimoveis.model.Imovel;
import com.avaliacaoimoveis.repository.ImovelRepository;
import com.avaliacaoimoveis.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ResultadoController {
    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/resultado")
    public String mostrarResultado(@RequestParam("id") Long id, Model model) {
        Optional<Imovel> imovelOpt = imovelRepository.findById(id);
        if (imovelOpt.isPresent()) {
            //model.addAttribute("avaliacao", imovelOpt.get());
            //return "resultado";
            Imovel imovel = imovelOpt.get();
            // Recalcular valor se for nulo
            if (imovel.getValorAvaliado() == null) {
                imovel = avaliacaoService.calcularValorImovel(imovel);
            }
            model.addAttribute("avaliacao", imovel);
            return "resultado";
        }
        return "redirect:/";
    }
}
