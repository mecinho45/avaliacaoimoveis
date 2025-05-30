package com.avaliacaoimoveis.controller;

import com.avaliacaoimoveis.model.ValorMetroQuadrado;
import com.avaliacaoimoveis.repository.ValorMetroQuadradoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


import java.util.List;

@RestController
@RequestMapping("/api/crm")
@RequiredArgsConstructor
public class CRMController {
    private final ValorMetroQuadradoRepository valorMetroQuadradoRepository;

    @GetMapping("/valores")
    public ResponseEntity<List<ValorMetroQuadrado>> listarValores() {
        return ResponseEntity.ok(valorMetroQuadradoRepository.findAll());
    }

    @PostMapping("/valores")
    public ResponseEntity<ValorMetroQuadrado> criarValor(
            @RequestBody @Valid ValorMetroQuadrado valor) {
        return ResponseEntity.ok(valorMetroQuadradoRepository.save(valor));
    }

    @PutMapping("/valores/{id}")
    public ResponseEntity<ValorMetroQuadrado> atualizarValor(
            @PathVariable Long id,
            @RequestBody @Valid ValorMetroQuadrado valorAtualizado) {
        return valorMetroQuadradoRepository.findById(id)
                .map(valor -> {
                    valor.setBairro(valorAtualizado.getBairro());
                    valor.setTipoImovel(valorAtualizado.getTipoImovel());
                    valor.setValor(valorAtualizado.getValor());
                    return ResponseEntity.ok(valorMetroQuadradoRepository.save(valor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/valores/{id}")
    public ResponseEntity<Void> deletarValor(@PathVariable Long id) {
        if (valorMetroQuadradoRepository.existsById(id)) {
            valorMetroQuadradoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}