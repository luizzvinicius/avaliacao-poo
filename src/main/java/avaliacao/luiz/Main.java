package avaliacao.luiz;

import avaliacao.luiz.domain.servicos.ClienteServico;
import avaliacao.luiz.domain.servicos.ProdutoServico;
import avaliacao.luiz.domain.servicos.VendaServico;
import avaliacao.luiz.infra.ConnectionFactory;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;

public class Main {
    static String[] opcoes = {"Cadastrar cliente", "Cadastrar Produto", "Vender Produto", "Listar clientes", "Sair"};
    static IMetodos[] metodos = {ClienteServico::cadastrarCliente, ProdutoServico::cadastrarProduto, VendaServico::venderProduto, ClienteServico::listarClientes};

    public static void main(String[] args) {
        try (final Connection conn = ConnectionFactory.getConn(); final Utils scan = new Utils()) {
            System.out.println("Bem-vindo a aplicação de vendas");
            while (true) {
                // System.out.println(conn);
                for (int i = 0; i < opcoes.length; i++) {
                    System.out.printf("%d- %s%n", i + 1, opcoes[i]);
                }
                int opt = scan.lerOption("Digite uma opção: ", 1, opcoes.length);
                if (opt + 1 == opcoes.length) break;
                metodos[opt].accept(conn, scan);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FunctionalInterface
    public interface IMetodos {
        void accept(Connection conn, Utils scanner);
    }
}