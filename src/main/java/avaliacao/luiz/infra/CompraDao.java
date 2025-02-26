package avaliacao.luiz.infra;

import avaliacao.luiz.domain.entities.Compra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class CompraDao {
    private final Connection conn;

    public CompraDao(Connection conn) {
        this.conn = conn;
    }

    public int insert(Compra c) {
        final String sql = "INSERT INTO compras (id_cliente, data_compra) VALUES (?,?)";
        int id = 0;
        try (var st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, c.getIdCliente());
            st.setTimestamp(2, Timestamp.valueOf(c.getDataCompra()));
            st.executeUpdate();
            var idGerado = st.getGeneratedKeys();
            if (idGerado.next()) id = idGerado.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar compra: " + e.getMessage());
        }
        return id;
    }

    public void updateIdPagamento(int id, int idPagamento) {
        final String sql = "UPDATE compras SET id_pagamento = ? WHERE id = ?";
        try (var st = conn.prepareStatement(sql)) {
            st.setInt(1, idPagamento);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar compra: " + e.getMessage());
        }
    }
}