package com.avaliacaoimoveis.dto;

import java.math.BigDecimal;

public record AvaliacaoDTO(
        Long id,
        String tipo,
        String bairro,
        BigDecimal metragem,
        BigDecimal valorAvaliado
) {}
