package com.avaliacaoimoveis.controller;

import com.avaliacaoimoveis.client.CepClient;
import com.avaliacaoimoveis.dto.AvaliacaoDTO;
import com.avaliacaoimoveis.model.Imovel;
import com.avaliacaoimoveis.repository.ImovelRepository;
import com.avaliacaoimoveis.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImovelController {
    private final AvaliacaoService avaliacaoService;
    private final CepClient viaCEPService;
    private final ImovelRepository imovelRepository;

    // Avaliar imóvel enviado diretamente no corpo da requisição
    @PostMapping("/avaliar")
    public ResponseEntity<Map<String, Object>> avaliarImovel(@RequestBody @Valid Imovel imovel) {
        Imovel imovelAvaliado = avaliacaoService.calcularValorImovel(imovel);
        AvaliacaoDTO dto = new AvaliacaoDTO(
                imovelAvaliado.getId(),
                imovelAvaliado.getTipo().toString(),
                imovelAvaliado.getEndereco().getBairro(),
                BigDecimal.valueOf(imovelAvaliado.getMetragem()),
                imovelAvaliado.getValorAvaliado()
        );
        // Adiciona URL de redirecionamento na resposta
        Map<String, Object> response = new HashMap<>();
        response.put("avaliacao", dto);
        response.put("redirectUrl", "/avaliacao-imoveis/resultado?id=" + imovelAvaliado.getId());
        return ResponseEntity.ok(response);
    }

    // Avaliar imóvel já cadastrado, por ID
    @GetMapping("/avaliar/{id}")
    public ResponseEntity<AvaliacaoDTO> avaliarImovelExistente(@PathVariable Long id) {
        Optional<Imovel> imovelOpt = imovelRepository.findById(id);
        if (imovelOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Imovel imovelAvaliado = avaliacaoService.calcularValorImovel(imovelOpt.get());
        AvaliacaoDTO dto = new AvaliacaoDTO(
                imovelAvaliado.getId(),
                imovelAvaliado.getTipo().toString(),
                imovelAvaliado.getEndereco().getBairro(),
                BigDecimal.valueOf(imovelAvaliado.getMetragem()),
                imovelAvaliado.getValorAvaliado()
        );
        return ResponseEntity.ok(dto);
    }
}
