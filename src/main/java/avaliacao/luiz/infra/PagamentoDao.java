package avaliacao.luiz.infra;

import avaliacao.luiz.domain.entities.Pagamento;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PagamentoDao {
    private final Connection conn;

    public PagamentoDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(Pagamento p) {
        final String sql = "INSERT INTO pagamentos (id_compra, tipo, valor_total) VALUES (?,?,?)";
        int id = 0;
        try (var st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, p.getIdCompra());
            st.setString(2, p.getTipo());
            st.setDouble(3, p.getTotal());
            st.executeUpdate();
            var idGerado = st.getGeneratedKeys();
            if (idGerado.next()) id = idGerado.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pagamento: " + e.getMessage());
        }
        return id;
    }
}