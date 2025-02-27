package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.Produto;
import avaliacao.luiz.domain.exceptions.EntradaInvalidaExpt;
import avaliacao.luiz.domain.exceptions.RegistroDuplicadoExpt;
import avaliacao.luiz.infra.ProdutoDao;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.util.List;

public class ProdutoServico {
    public static void cadastrarProduto(Connection conn, Utils util) {
        var produtoConn = new ProdutoDao(conn);
        String nome = util.lerString("\nNome do produto: ", "Nome inválido");
        try {
            if (produtoConn.select(nome).isPresent()) throw new RegistroDuplicadoExpt("nome");
        } catch (RegistroDuplicadoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        double preco = util.lerDouble("Preço do produto: ");
        try {
            if (preco <= 0) throw new EntradaInvalidaExpt("Preço");
        } catch (EntradaInvalidaExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        double quantidade = util.lerDouble("Quantidade do produto: ");
        try {
            if (quantidade <= 0) throw new EntradaInvalidaExpt("Quantidade");
        } catch (EntradaInvalidaExpt e) {
            System.out.println(e.getMessage());
            return;
        }
        produtoConn.insert(new Produto(nome, preco, quantidade));
    }

    public static List<Produto> select(Connection conn) {
        return new ProdutoDao(conn).select();
    }

    public static void updateQuantidade(Connection conn, Produto p, double novaQtd) {
        new ProdutoDao(conn).updateQuantidade(p, novaQtd);
    }
}