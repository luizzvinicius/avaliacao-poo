package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.ItemVenda;
import avaliacao.luiz.infra.ItemVendaDao;

import java.sql.Connection;

public class ItemVendaServico {
    public static void insertItemServico(Connection conn, ItemVenda i) {
        new ItemVendaDao(conn).insert(i);
    }
}