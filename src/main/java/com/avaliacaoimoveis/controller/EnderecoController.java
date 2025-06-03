package com.avaliacaoimoveis.controller;

import com.avaliacaoimoveis.dto.EnderecoViaCepDTO;
import com.avaliacaoimoveis.service.CepService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ceps")
@RequiredArgsConstructor
@Validated // Necessário para ativar validação em métodos de controladores
public class EnderecoController {

    private final CepService cepService;

    @GetMapping("{cep}")
    EnderecoViaCepDTO consultarCep(
        @PathVariable
        @NotBlank(message = "O CEP não pode estar em branco")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "O CEP deve estar no formato 99999-999 ou 99999999")
        String cep
    ) {
        return cepService.buscarEnderecoPorCep(cep);
    }
}
