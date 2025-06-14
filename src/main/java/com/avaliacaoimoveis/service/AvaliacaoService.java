package com.avaliacaoimoveis.service;

import com.avaliacaoimoveis.model.Imovel;
import com.avaliacaoimoveis.repository.ImovelRepository;
import com.avaliacaoimoveis.model.Proprietario;
import com.avaliacaoimoveis.repository.ProprietarioRepository;
import com.avaliacaoimoveis.model.ValorMetroQuadrado;
import com.avaliacaoimoveis.repository.ValorMetroQuadradoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {
    private final ValorMetroQuadradoRepository valorMetroQuadradoRepository;
    private final ImovelRepository imovelRepository;
    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Imovel calcularValorImovel(Imovel imovel) {
        Proprietario proprietario = imovel.getProprietario();
        proprietarioRepository.save(proprietario);

        Optional<ValorMetroQuadrado> valorOpt = valorMetroQuadradoRepository
                .findByBairroAndTipoImovel(imovel.getEndereco().getBairro(), imovel.getTipo());

        if (valorOpt.isPresent()) {
            ValorMetroQuadrado valorMetro = valorOpt.get();
            BigDecimal valorCalculado = valorMetro.getValor()
                    .multiply(BigDecimal.valueOf(imovel.getMetragem()))
                    .setScale(2, RoundingMode.HALF_UP);

            // Ajustes baseados em características do imóvel
            valorCalculado = aplicarAjustes(valorCalculado, imovel);
            imovel.setValorAvaliado(valorCalculado);
        } else {
            // Valor padrão quando não encontrar o valor do metro quadrado
            BigDecimal valorPadrao = new BigDecimal("8000.00")
                    .multiply(BigDecimal.valueOf(imovel.getMetragem()))
                    .setScale(2, RoundingMode.HALF_UP);
            imovel.setValorAvaliado(valorPadrao);
        }
        return imovelRepository.save(imovel);
        //return imovel;
    }

    private BigDecimal aplicarAjustes(BigDecimal valorBase, Imovel imovel) {
        BigDecimal valorAjustado = valorBase;

        // Ajuste por quartos (5% por quarto adicional)
        if (imovel.getQuartos() > 2) {
            BigDecimal ajusteQuartos = valorBase.multiply(
                    BigDecimal.valueOf(0.05 * (imovel.getQuartos() - 2)));
            valorAjustado = valorAjustado.add(ajusteQuartos);
        }

        // Ajuste por vagas (3% por vaga adicional)
        if (imovel.getVagas() > 1) {
            BigDecimal ajusteVagas = valorBase.multiply(
                    BigDecimal.valueOf(0.03 * (imovel.getVagas() - 1)));
            valorAjustado = valorAjustado.add(ajusteVagas);
        }

        return valorAjustado.setScale(2, RoundingMode.HALF_UP);
    }
}


