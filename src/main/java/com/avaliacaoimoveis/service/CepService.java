package com.avaliacaoimoveis.service;

import com.avaliacaoimoveis.client.CepClient;
import com.avaliacaoimoveis.dto.EnderecoViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CepService {

    private final CepClient viaCepClient;

    public EnderecoViaCepDTO buscarEnderecoPorCep(String cep) {
        return viaCepClient.consultarCep(cep);
    }
}
