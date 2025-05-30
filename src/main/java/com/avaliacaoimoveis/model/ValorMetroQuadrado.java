package com.avaliacaoimoveis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "valores_metro_quadrado")
public class ValorMetroQuadrado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotNull(message = "Tipo de imóvel é obrigatório")
    @Enumerated(EnumType.STRING)
    private Imovel.TipoImovel tipoImovel;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;
}