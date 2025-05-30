package com.avaliacaoimoveis.controller;

import com.avaliacaoimoveis.dto.EnderecoViaCepDTO;
import com.avaliacaoimoveis.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ceps")
@RequiredArgsConstructor
public class EnderecoController {

    private final CepService cepService;

    @GetMapping("{cep}")
    EnderecoViaCepDTO consultarCep(@PathVariable String cep){
        return cepService.buscarEnderecoPorCep(cep);
    }
}
