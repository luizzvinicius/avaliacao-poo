package domain.servicos;

import avaliacao.luiz.domain.entities.Compra;
import avaliacao.luiz.domain.servicos.CompraServico;
import avaliacao.luiz.infra.CompraDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private CompraDao compraDao;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private CompraServico compraServico;

    @Test
    @DisplayName("insert Compra deve chamar insert() do DAO e retornar o ID gerado")
    void deveInserirCompraERetornarId() throws SQLException {
        int idEsperado = 10;
        Compra compra = new Compra(idEsperado);

        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(idEsperado);
        when(st.getGeneratedKeys()).thenReturn(resultSet);
        when(st.getGeneratedKeys().next()).thenReturn(true);
        when(compraDao.insert(compra)).thenReturn(idEsperado);

        CompraServico.insert(conn, compra);
        var resultado = compraDao.insert(compra);

        assertEquals(idEsperado, resultado);
        verify(compraDao).insert(compra);
    }

    @Test
    @DisplayName("updateIdPagamento deve chamar updateIdPagamento() do DAO")
    void deveAtualizarIdPagamento() throws SQLException {
        int idCompra = 5;
        int idPagamento = 100;

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1); // linhas afetadas

        CompraServico.updateIdPagamento(conn, idCompra, idPagamento);
        compraDao.updateIdPagamento(idCompra, idPagamento);

        verify(compraDao).updateIdPagamento(idCompra, idPagamento);
    }
}