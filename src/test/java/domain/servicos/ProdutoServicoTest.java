package domain.servicos;

import avaliacao.luiz.domain.entities.Produto;
import avaliacao.luiz.domain.exceptions.EntradaInvalidaExpt;
import avaliacao.luiz.domain.exceptions.RegistroDuplicadoExpt;
import avaliacao.luiz.domain.servicos.ProdutoServico;
import avaliacao.luiz.infra.ProdutoDao;
import avaliacao.luiz.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private Utils util;

    @Mock
    private ProdutoDao produtoDao;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProdutoServico produtoServico;

    @Test
    void deveInsertComSucesso() throws SQLException {
        Produto p = new Produto("gabinete", 420.0, 70);

        when(util.lerString(anyString(), anyString())).thenReturn(p.getNome()); // nome
        when(util.lerDouble(anyString())).thenReturn(p.getPreco()); // preco
        when(util.lerDouble(anyString())).thenReturn(p.getQuantidade()); // quantidade

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);

        ProdutoServico.insert(conn, util);
        produtoDao.insert(p);

        verify(produtoDao, times(1)).insert(p);
    }

    @Test
    void naoDeveInsertQuandoNomeJaExiste() throws SQLException {
        Produto p = new Produto("gabinete", 420.0, 70);

        when(util.lerString(anyString(), anyString())).thenReturn(p.getNome()); // nome
        when(produtoDao.select(p.getNome())).thenThrow(new RegistroDuplicadoExpt("nome"));
        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);

        ProdutoServico.insert(conn, util);
        var expt = assertThrows(RegistroDuplicadoExpt.class, () -> {
            produtoDao.select(p.getNome());
        });

        assertEquals("nome já cadastrado\n", expt.getMessage());
        verify(produtoDao, never()).insert(any(Produto.class));
    }

    @Test
    void naoDeveInsertComPrecoInvalido() throws SQLException {
        Produto p = new Produto("gabinete", 420.0, 70);

        when(util.lerString(anyString(), anyString())).thenReturn(p.getNome()); // nome
        when(util.lerDouble(anyString())).thenThrow(new EntradaInvalidaExpt("Preço")); // preco
        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);

        var expt = assertThrows(EntradaInvalidaExpt.class, () -> {
            ProdutoServico.insert(conn, util);
        });

        assertEquals("Preço inválido\n", expt.getMessage());
        verify(produtoDao, never()).insert(any(Produto.class));
    }

    @Test
    void naoDeveInsertComQuantidadeInvalida() throws SQLException {
        Produto p = new Produto("gabinete", 420.0, 70);

        when(util.lerString(anyString(), anyString())).thenReturn(p.getNome()); // nome
        when(util.lerDouble(anyString())).thenReturn(p.getPreco()); // preco
        when(util.lerDouble(anyString())).thenThrow(new EntradaInvalidaExpt("Quantidade")); // preco
        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);

        var expt = assertThrows(EntradaInvalidaExpt.class, () -> {
            ProdutoServico.insert(conn, util);
        });

        assertEquals("Quantidade inválido\n", expt.getMessage());
        verify(produtoDao, never()).insert(any(Produto.class));
    }

    @Test
    void deveSelecionarTodosProdutos() {
        List<Produto> produtos = List.of(
                new Produto("Teclado", 150.0, 10),
                new Produto("Mouse", 100.0, 20)
        );

        when(produtoDao.select()).thenReturn(produtos);

        var resultado = produtoDao.select();

        assertEquals(produtos, resultado);
    }

    @Test
    void deveAtualizarQuantidadeDoProduto() throws SQLException {
        Produto produto = new Produto("Cadeira", 300.0, 5);
        double novaQuantidade = 10.0;

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1); // linhas afetadas

        ProdutoServico.updateQuantidade(conn, produto, novaQuantidade);
        produtoDao.updateQuantidade(produto, novaQuantidade);

        verify(produtoDao, times(1)).updateQuantidade(produto, novaQuantidade);
    }
}