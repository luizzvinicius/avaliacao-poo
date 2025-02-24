package avaliacao.luiz.infra;

import avaliacao.luiz.domain.entities.Produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {
    private final Connection conn;

    public ProdutoDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(Produto p) {
        final String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?,?,?);";
        try (var st = conn.prepareStatement(sql)) {
            st.setString(1, p.getNome());
            st.setDouble(2, p.getPreco());
            st.setDouble(3, p.getQuantidade());
            st.executeUpdate();
            System.out.println("Produto inserido.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public void updateQuantidade(Produto p, double novaQtd) {
        final String sql = "UPDATE produto SET quantidade = ? WHERE id = ?;";
        try (var st = conn.prepareStatement(sql)) {
            st.setDouble(1, novaQtd);
            st.setInt(2, p.getId());
            st.executeUpdate();
            System.out.println("Quantidade atualizada.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public List<Produto> selectAll() {
        final String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        try (var st = conn.prepareStatement(sql)) {
            var result = st.executeQuery();
            while (result.next()) {
                produtos.add(new Produto(
                        result.getInt("id"), result.getString("nome"),
                        result.getDouble("preco"), result.getDouble("quantidade")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar produto: " + e.getMessage());
        }
        return produtos;
    }
}