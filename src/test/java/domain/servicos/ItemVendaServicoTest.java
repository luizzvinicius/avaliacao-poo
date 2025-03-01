package domain.servicos;

import avaliacao.luiz.domain.entities.ItemVenda;
import avaliacao.luiz.domain.servicos.ItemVendaServico;
import avaliacao.luiz.infra.ItemVendaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemVendaServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private ItemVendaDao itemVendaDao;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ItemVendaServico clienteServico;

    @Test
    void deveInserirItemVendaCorretamente() throws SQLException {
        ItemVenda item = new ItemVenda(1, 2, 10.50, 3);

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1); // linhas afetadas

        ItemVendaServico.insert(conn, item);
        itemVendaDao.insert(item);

        verify(itemVendaDao, times(1)).insert(any(ItemVenda.class));
    }
}