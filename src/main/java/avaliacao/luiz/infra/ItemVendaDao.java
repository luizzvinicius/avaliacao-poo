package avaliacao.luiz.infra;

import avaliacao.luiz.domain.entities.ItemVenda;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemVendaDao {
    private final Connection conn;

    public ItemVendaDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(ItemVenda i) {
        final String sql = "INSERT INTO item_venda (id_compra, id_produto, preco, quantidade) VALUES (?,?,?,?)";
        try (var st = conn.prepareStatement(sql)) {
            st.setInt(1, i.getIdCompra());
            st.setInt(2, i.getIdProduto());
            st.setDouble(3, i.getPreco());
            st.setDouble(4, i.getQuantidade());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar item-venda: " + e.getMessage());
        }

    }
}