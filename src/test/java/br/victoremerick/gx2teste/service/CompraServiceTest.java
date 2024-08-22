package br.victoremerick.gx2teste.service;

import br.victoremerick.gx2teste.model.dto.Cliente;
import br.victoremerick.gx2teste.model.dto.Compra;
import br.victoremerick.gx2teste.model.dto.Produto;
import br.victoremerick.gx2teste.model.dto.RecomendacaoVinho;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private List<Produto> produtos;

    @Mock
    private List<Cliente> clientes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Produto produto1 = new Produto();
        produto1.setCodigo(1);
        produto1.setTipo_vinho("Tinto");
        produto1.setPreco(229.99);
        produto1.setSafra("2017");
        produto1.setAno_compra(2018);

        Produto produto2 = new Produto();
        produto2.setCodigo(15);
        produto2.setTipo_vinho("Branco");
        produto2.setPreco(150.00);
        produto2.setSafra("2016");
        produto2.setAno_compra(2018);

        Cliente cliente = new Cliente();
        cliente.setNome("Geraldo Pedro Julio Nascimento");
        cliente.setCpf("05870189179");

        Compra compra1 = new Compra();
        compra1.setCodigo("1");
        compra1.setQuantidade(6);

        Compra compra2 = new Compra();
        compra2.setCodigo("15");
        compra2.setQuantidade(4);

        cliente.setCompras(Arrays.asList(compra1, compra2));

        compraService.setProdutos(Arrays.asList(produto1, produto2));
        compraService.setClientes(Arrays.asList(cliente));
    }

    @Test
    public void testGetCompras() {
        List<Map<String, Object>> compras = compraService.getCompras();
        assertEquals(2, compras.size());

        Map<String, Object> compra1 = compras.get(0);
        assertEquals("Geraldo Pedro Julio Nascimento", compra1.get("nomeCliente"));
        assertEquals("05870189179", compra1.get("cpfCliente"));
        assertEquals("Branco", compra1.get("produto"));
        assertEquals(600.00, compra1.get("valorTotal"));

        Map<String, Object> compra2 = compras.get(1);
        assertEquals("Geraldo Pedro Julio Nascimento", compra2.get("nomeCliente"));
        assertEquals("05870189179", compra2.get("cpfCliente"));
        assertEquals("Tinto", compra2.get("produto"));
        assertEquals(1379.94, compra2.get("valorTotal"));
    }

    @Test
    public void testGetMaiorCompraPorAno() {
        Map<String, Object> maiorCompra = compraService.getMaiorCompraPorAno(2018);
        assertEquals("Geraldo Pedro Julio Nascimento", maiorCompra.get("nomeCliente"));
        assertEquals("05870189179", maiorCompra.get("cpfCliente"));
        assertEquals("Tinto", maiorCompra.get("produto"));
        assertEquals(1379.94, maiorCompra.get("valorTotal"));
    }

    @Test
    public void testGetClientesFieis() {
        List<Map<String, Object>> clientesFieis = compraService.getClientesFieis();
        assertEquals(1, clientesFieis.size());

        Map<String, Object> cliente = clientesFieis.get(0);
        assertEquals("05870189179", cliente.get("cpfCliente"));
        assertEquals(1979.94, cliente.get("valorTotal"));
    }

    @Test
    public void testGetRecomendacao() {
        Optional<RecomendacaoVinho> recomendacoes = compraService.getRecomendacao("05870189179");

        RecomendacaoVinho produto = recomendacoes.get();
        assertEquals("Tinto", produto.getTipo_vinho());
    }

}

