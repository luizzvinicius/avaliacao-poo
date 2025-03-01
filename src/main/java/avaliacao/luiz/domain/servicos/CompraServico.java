package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.Compra;
import avaliacao.luiz.infra.CompraDao;

import java.sql.Connection;

public class CompraServico {
    public static int insert(Connection conn, Compra c) {
        return new CompraDao(conn).insert(c);
    }

    public static void updateIdPagamento(Connection conn, int idCompra, int idPagamento) {
        new CompraDao(conn).updateIdPagamento(idCompra, idPagamento);
    }
}
