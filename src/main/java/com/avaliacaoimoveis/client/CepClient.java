package com.avaliacaoimoveis.client;
import com.avaliacaoimoveis.dto.EnderecoViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CepClient", url = "https://viacep.com.br/ws")
public interface CepClient {
        @GetMapping("/{cep}/json")
        EnderecoViaCepDTO consultarCep(@PathVariable("cep") String cep);
}
