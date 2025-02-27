package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.Pagamento;
import avaliacao.luiz.infra.PagamentoDao;

import java.sql.Connection;

public class PagamentoServico {
    public static int insertPagamento(Connection conn, Pagamento p) {
        return new PagamentoDao(conn).insert(p);
    }
}