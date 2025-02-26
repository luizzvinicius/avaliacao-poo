package avaliacao.luiz;

import avaliacao.luiz.domain.entities.*;
import avaliacao.luiz.infra.*;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String[] opcoes = {"Cadastrar cliente", "Cadastrar Produto", "Vender Produto", "Listar clientes", "Sair"};
    static IMetodos[] metodos = {Main::cadastrarCliente, Main::cadastrarProduto, Main::venderProduto, Main::listarClientes};

    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConn(); Utils scan = new Utils()) {
            while (true) {
                System.out.println(conn);
                System.out.println("Bem-vindo a aplicação de vendas");
                for (int i = 0; i < opcoes.length; i++) {
                    System.out.printf("%d- %s%n", i + 1, opcoes[i]);
                }
                int opt = scan.lerOption("Digite uma opção: ", 1, opcoes.length, "Opção inválida");
                if (opt + 1 == opcoes.length) break;
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
        // verificar se telefone, cpf e cnpj está cadastrado
        new ClienteDao(conn).insert(cliente);
    }

    private static void cadastrarProduto(Connection conn, Utils scanner) {
        String nome = scanner.lerString("\nNome do produto: ", "Nome inválido");
        double preco = scanner.lerDouble("Preço do produto: ");
        double quantidade = scanner.lerDouble("Quantidade do produto: ");

        // validar preco e quantidade

        Produto p = new Produto(nome, preco, quantidade);
        new ProdutoDao(conn).insert(p);
    }

    private static void venderProduto(Connection conn, Utils scanner) {
        var clienteconn = new ClienteDao(conn);
        int clienteId = scanner.lerInt("Digite o id do cliente: ");
        Cliente c = clienteconn.select(clienteId).orElseThrow(() -> new NotFoundExpt("cliente"));

        var produtoconn = new ProdutoDao(conn);
        List<Produto> produtos = produtoconn.selectAll();
        List<Produto> carrinho = new ArrayList<>();
        while (true) {
            List<Produto> produtosFiltrados = produtos.stream().filter(p -> p.getQuantidade() > 0).toList();
            try {
                if (produtosFiltrados.isEmpty()) throw new NotFoundExpt("Produto");
            } catch (NotFoundExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            scanner.mostraArrayFormatado(produtosFiltrados);
            int indexProduto = scanner.lerOption("Selecione o produto: ", 1, produtos.size(), "Produto selecionado inválido");
            Produto p = produtos.get(indexProduto);
            double quantidade = scanner.lerOption("Selecione a quantidade de " + p.getNome() + ": ", 1, (int) p.getQuantidade(), "Quantidade inválida") + 1;
            System.out.println(p.getNome() + " selecionada");
            carrinho.add(new Produto(p.getId(), p.getNome(), p.getPreco(), quantidade));
            p.setQuantidade(p.getQuantidade() - quantidade);
            System.out.println(produtos);
            System.out.println("Deseja adicionar mais produtos no carrinho?");
            System.out.println("1- Sim\n2- Não");
            int continuar = scanner.lerOption("Opção: ", 1, 2, "Opção inválida") + 1;
            if (continuar == 2) break;
        }
        System.out.println("----- Fechamento da conta -----");
        System.out.println("Cliente: " + c);
        scanner.mostraArrayFormatado(carrinho);
        double total = carrinho.stream().reduce(0d, (acc, p) -> acc + (p.getQuantidade() * p.getPreco()), Double::sum);
        System.out.println("Total R$ " + total);
        // forma pagamento
        // inserir no banco
    }

    private static void listarClientes(Connection conn, Utils scanner) {
        var clienteConn = new ClienteDao(conn);
        var clientes = clienteConn.selectAll();
        scanner.mostraArrayFormatado(clientes);
    }

    @FunctionalInterface
    public interface IMetodos {
        void accept(Connection conn, Utils scanner);
    }
}