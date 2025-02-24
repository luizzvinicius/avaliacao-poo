package avaliacao.luiz;

import avaliacao.luiz.domain.entities.Cliente;
import avaliacao.luiz.domain.entities.Empresa;
import avaliacao.luiz.domain.entities.PessoaFisica;
import avaliacao.luiz.domain.entities.Produto;
import avaliacao.luiz.infra.ConnectionFactory;
import avaliacao.luiz.infra.ProdutoDao;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    static String[] opcoes = {"Cadastrar cliente", "Cadastrar Produto", "Vender Produto", "Sair"};
    static IMetodos[] metodos = {Main::cadastrarCliente, Main::cadastrarProduto, Main::venderProduto};

    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConn(); Utils scan = new Utils()) {
            while (true) {
                System.out.println(conn);
                System.out.println("Bem-vindo a aplicação de vendas");
                for (int i = 0; i < opcoes.length; i++) {
                    System.out.printf("%d- %s%n", i + 1, opcoes[i]);
                }
                int opt = scan.lerOption("Digite uma opção: ", 1, opcoes.length, "Opção inválida");
                if (opt+1 == opcoes.length) break;
                metodos[opt].accept(conn, scan);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cadastrarCliente(Connection conn, Utils scanner) {
        System.out.println("\nO cliente é pessoa física ou jurídica?\n1- Física\n2- Jurídica");
        int opt = scanner.lerOption("Digite uma opção: ", 1, 2, "Opção inválida");
        String nome = scanner.lerString("Nome do cliente: ", "Nome inválido");
        String telefone = scanner.lerOnzeDigitos("Telefone do cliente (Apenas números): ", "Telefone inválido");

        Cliente cliente;
        if (opt == 0) {
            String cpf = scanner.lerOnzeDigitos("CPF do cliente (Apenas números): ", "CPF inválido");
            LocalDate dataNascimento = scanner.lerData("Data de nascimento (xx/xx/xxxx): ");
            cliente = new PessoaFisica(nome, telefone, cpf, dataNascimento);
        } else {
            String cnpj = scanner.lerCnpj("CNPJ do cliente (Apenas números): ", "CNPJ inválido");
            String nomeEmpresa = scanner.lerString("Nome da empresa: ", "Nome inválido");
            cliente = new Empresa(nome, telefone, cnpj, nomeEmpresa);
        }
        // salvar no banco
    }

    private static void cadastrarProduto(Connection conn, Utils scanner) {
        String nome = scanner.lerString("\nNome do produto: ", "Nome inválido");
        double preco = scanner.lerDouble("Preço do produto: ");
        double quantidade = scanner.lerDouble("Quantidade do produto: ");

        Produto p = new Produto(nome, preco, quantidade);
        new ProdutoDao(conn).insert(p);
    }

    private static void venderProduto(Connection conn, Utils scanner) {
        System.out.println("Vender Produto");
    }

    @FunctionalInterface
    public interface IMetodos {
        void accept(Connection conn, Utils scanner);
    }
}