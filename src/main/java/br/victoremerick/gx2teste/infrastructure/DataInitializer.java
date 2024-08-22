package br.victoremerick.gx2teste.infrastructure;

import br.victoremerick.gx2teste.model.dto.Cliente;
import br.victoremerick.gx2teste.model.dto.Produto;
import br.victoremerick.gx2teste.service.CompraService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataInitializer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompraService compraService;

    private final String produtosUrl = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json";
    private final String clientesUrl = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json";

    @PostConstruct
    public void init() {
        Produto[] produtos = restTemplate.getForObject(produtosUrl, Produto[].class);
        Cliente[] clientes = restTemplate.getForObject(clientesUrl, Cliente[].class);

        List<Produto> produtoList = Arrays.asList(produtos);
        List<Cliente> clienteList = Arrays.asList(clientes);

        compraService.setProdutos(produtoList);
        compraService.setClientes(clienteList);
    }
}


