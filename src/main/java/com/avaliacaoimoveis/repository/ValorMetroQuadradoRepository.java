package com.avaliacaoimoveis.repository;

import com.avaliacaoimoveis.model.ValorMetroQuadrado;
import com.avaliacaoimoveis.model.Imovel.TipoImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ValorMetroQuadradoRepository extends JpaRepository<ValorMetroQuadrado, Long> {
    Optional<ValorMetroQuadrado> findByBairroAndTipoImovel(String bairro, TipoImovel tipoImovel);
}