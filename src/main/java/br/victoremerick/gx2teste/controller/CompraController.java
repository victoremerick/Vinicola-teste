package br.victoremerick.gx2teste.controller;

import br.victoremerick.gx2teste.model.dto.Produto;
import br.victoremerick.gx2teste.model.dto.RecomendacaoVinho;
import br.victoremerick.gx2teste.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping("/compras")
    public List<Map<String, Object>> getCompras() {
        return compraService.getCompras();
    }

    @GetMapping("/maior-compra/{ano}")
    public Map<String, Object> getMaiorCompraPorAno(@PathVariable int ano) {
        return compraService.getMaiorCompraPorAno(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<Map<String, Object>> getClientesFieis() {
        return compraService.getClientesFieis();
    }

    @GetMapping("/recomendacao/cliente/{cpf}/tipo")
    public Optional<RecomendacaoVinho> getRecomendacao(@PathVariable String cpf) {
        return compraService.getRecomendacao(cpf);
    }
}


