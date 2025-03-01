package domain.servicos;

import avaliacao.luiz.domain.entities.Pagamento;
import avaliacao.luiz.domain.servicos.PagamentoServico;
import avaliacao.luiz.infra.PagamentoDao;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private PagamentoDao pagamentoDao;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private PagamentoServico pagamentoServico;

    @Test()
    @DisplayName("Deve inserir pagamento corretamente")
    void inserirPagamento() throws SQLException {
        int idPagamentoEsperado = 25;
        Pagamento p = new Pagamento("PIX", 10, 500.00);

        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1); // linhas afetadas
        when(st.getGeneratedKeys()).thenReturn(resultSet);
        when(st.getGeneratedKeys().next()).thenReturn(true);
        when(pagamentoDao.insert(p)).thenReturn(idPagamentoEsperado);

        PagamentoServico.insert(conn, p);
        int result = pagamentoDao.insert(p);

        assertEquals(idPagamentoEsperado, result);
        verify(pagamentoDao, times(1)).insert(p);
    }
}