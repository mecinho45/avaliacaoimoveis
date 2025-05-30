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
@Table(name = "imoveis")
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tipo do imóvel é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoImovel tipo;

    @NotNull(message = "Metragem é obrigatória")
    @Positive(message = "Metragem deve ser positiva")
    private Double metragem;

    @NotNull(message = "Quantidade de quartos é obrigatória")
    @Min(value = 0, message = "Quantidade de quartos não pode ser negativa")
    private Integer quartos;

    @NotNull(message = "Quantidade de banheiros é obrigatória")
    @Min(value = 1, message = "Deve ter pelo menos 1 banheiro")
    private Integer banheiros;

    @NotNull(message = "Quantidade de vagas é obrigatória")
    @Min(value = 0, message = "Quantidade de vagas não pode ser negativa")
    private Integer vagas;

    private String unidade; // Para apartamentos

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    @NotNull(message = "Endereço é obrigatório")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    @NotNull(message = "Proprietário é obrigatório")
    private Proprietario proprietario;

    @Transient
    private BigDecimal valorAvaliado;

    public enum TipoImovel {
        RESIDENCIAL, COMERCIAL
    }
}