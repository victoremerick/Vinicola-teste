package br.victoremerick.gx2teste.service;
import br.victoremerick.gx2teste.model.dto.Cliente;
import br.victoremerick.gx2teste.model.dto.Produto;
import br.victoremerick.gx2teste.model.dto.RecomendacaoVinho;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private List<Produto> produtos;
    private List<Cliente> clientes;

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    // 1. GET: /compras
    public List<Map<String, Object>> getCompras() {
        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto produto = produtos.stream()
                                    .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                                    .findFirst()
                                    .orElse(null);

                            if (produto == null) {
                                return null;
                            }

                            Map<String, Object> result = new HashMap<>();
                            result.put("nomeCliente", cliente.getNome());
                            result.put("cpfCliente", cliente.getCpf());
                            result.put("produto", produto.getTipo_vinho());
                            result.put("quantidade", compra.getQuantidade());
                            result.put("valorTotal", produto.getPreco() * compra.getQuantidade());
                            return result;
                        })
                        .filter(Objects::nonNull)
                )
                .sorted(Comparator.comparingDouble(o -> (Double) o.get("valorTotal")))
                .collect(Collectors.toList());
    }

    // 2. GET: /maior-compra/{ano}
    public Map<String, Object> getMaiorCompraPorAno(int ano) {
        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto produto = produtos.stream()
                                    .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                                    .findFirst()
                                    .orElse(null);

                            if (produto == null || produto.getAno_compra() != ano) {
                                return null;
                            }

                            Map<String, Object> result = new HashMap<>();
                            result.put("nomeCliente", cliente.getNome());
                            result.put("cpfCliente", cliente.getCpf());
                            result.put("produto", produto.getTipo_vinho());
                            result.put("quantidade", compra.getQuantidade());
                            result.put("valorTotal", produto.getPreco() * compra.getQuantidade());
                            return result;
                        })
                        .filter(Objects::nonNull)
                )
                .max(Comparator.comparingDouble(o -> (Double) o.get("valorTotal")))
                .orElse(null);
    }

    // 3. GET: /clientes-fieis
    public List<Map<String, Object>> getClientesFieis() {
        return clientes.stream()
                .collect(Collectors.toMap(
                        Cliente::getCpf,
                        cliente -> cliente.getCompras().stream()
                                .mapToDouble(compra -> {
                                    Produto produto = produtos.stream()
                                            .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                                            .findFirst()
                                            .orElse(null);

                                    return (produto != null) ? produto.getPreco() * compra.getQuantidade() : 0;
                                })
                                .sum(),
                        Double::sum
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .map(e -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("cpfCliente", e.getKey());
                    result.put("valorTotal", e.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    // 4. GET: /recomendacao/cliente/{cpf}/tipo
    public Optional<RecomendacaoVinho> getRecomendacao(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto produto = produtos.stream()
                                    .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                                    .findFirst()
                                    .orElse(null);

                            return (produto != null) ? produto.getTipo_vinho() : null;
                        })
                        .filter(Objects::nonNull)
                )
                .collect(Collectors.groupingBy(tipo -> tipo, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.<String, Long>comparingByValue())
                .map(Map.Entry::getKey)
                .map(tipo -> produtos.stream()
                        .filter(p -> p.getTipo_vinho().equals(tipo))
                        .collect(Collectors.toList())
                ).flatMap(produtos1 -> produtos1.stream().findFirst())
                .map(produto -> RecomendacaoVinho.builder()
                        .tipo_vinho(produto.getTipo_vinho())
                        .safra(produto.getSafra())
                        .build());
    }
}
