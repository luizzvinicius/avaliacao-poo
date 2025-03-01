package domain.servicos;

import avaliacao.luiz.domain.entities.*;
import avaliacao.luiz.domain.servicos.*;
import avaliacao.luiz.infra.ClienteDao;
import avaliacao.luiz.infra.CompraDao;
import avaliacao.luiz.infra.ItemVendaDao;
import avaliacao.luiz.infra.PagamentoDao;
import avaliacao.luiz.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private Utils utils;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ProdutoServico produtoServico;

    @Mock
    private CompraDao compraDao;

    @Mock
    private ItemVendaDao itemVendaDao;

    @Mock
    private PagamentoDao pagamentoDao;

    @InjectMocks
    private VendaServico vendaServico;

    @Test
    void deveVenderProdutoCorretamente() {
        int idCliente = 1;
        Cliente cliente = new PessoaFisica(idCliente, "Ana", "11987654321", "11122233344", LocalDate.now());

        doReturn(Optional.of(cliente)).when(clienteDao).select(anyInt());

        mockStatic(ClienteServico.class);
        when(ClienteServico.select(conn, utils)).thenReturn(cliente);

        clienteDao.select(anyInt());

        List<Produto> produtos = List.of(
                new Produto("Teclado", 150.0, 10),
                new Produto("Mouse", 100.0, 20)
        );
        Mockito.mockStatic(ProdutoServico.class);
        when(ProdutoServico.select(conn)).thenReturn(produtos);


        List<Produto> carrinho = List.of(produtos.getFirst());
        var teclado = carrinho.getFirst();
        Compra compra = new Compra(cliente.getId());
        int idCompra = 57;

        when(utils.lerOption("Selecione o produto: ", 1, produtos.size(), "Produto selecionado inválido")).thenReturn(0);
        when(utils.lerOption("Selecione a quantidade de " + teclado.getNome() + ": ", 1, (int) produtos.getFirst().getQuantidade(), "Quantidade inválida")).thenReturn(1);

        mockStatic(CompraServico.class);
        when(CompraServico.insert(conn, compra)).thenReturn(idCompra);


        ProdutoServico.updateQuantidade(conn, carrinho.getFirst(), produtos.getFirst().getQuantidade() - carrinho.getFirst().getQuantidade());

        mockStatic(ItemVendaServico.class);
        ItemVenda itemVenda = new ItemVenda(1, idCompra, carrinho.getFirst().getPreco(), carrinho.getFirst().getQuantidade());


        when(utils.lerOption("Deseja adicionar mais produtos no carrinho?\n1- Sim\n2- Não\nOpção: ", 1, 2)).thenReturn(1);

        int idPagamento = 5;
        Pagamento pagamento = new Pagamento("PIX", idCompra, 150);
        mockStatic(PagamentoServico.class);

        when(PagamentoServico.insert(conn, pagamento)).thenReturn(1);
        CompraServico.updateIdPagamento(conn, idCompra, idPagamento);

        VendaServico.venderProduto(conn, utils);

        assertNotNull(cliente, "O cliente não deveria ser nulo.");
        assertEquals(2, produtos.size(), "A lista de produtos deveria ter 2 itens.");
        assertEquals("Teclado", produtos.get(0).getNome(), "O primeiro produto deveria ser um Teclado.");
        assertEquals(150.0, produtos.get(0).getPreco(), "O preço do teclado deveria ser 150.");
        assertTrue((teclado.getQuantidade() > 1 && teclado.getQuantidade() < 11));
        assertEquals("PIX", pagamento.getTipo(), "O pagamento deveria ser via PIX.");
        assertEquals(150, pagamento.getTotal(), "O valor do pagamento deveria ser 150.");

        verify(clienteDao, times(1)).select(anyInt());
        verify(utils, times(1)).lerOption("Selecione o produto: ", 1, produtos.size(), "Produto selecionado inválido");
    }
}