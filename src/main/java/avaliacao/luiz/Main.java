package avaliacao.luiz;

import avaliacao.luiz.domain.entities.*;
import avaliacao.luiz.domain.entities.metodos_pagamento.Boleto;
import avaliacao.luiz.domain.entities.metodos_pagamento.CartaoCredito;
import avaliacao.luiz.domain.entities.metodos_pagamento.MetodoPagamento;
import avaliacao.luiz.domain.entities.metodos_pagamento.Pix;
import avaliacao.luiz.domain.exceptions.EntradaInvalidaExpt;
import avaliacao.luiz.domain.exceptions.NaoEncontradoExpt;
import avaliacao.luiz.domain.exceptions.RegistroDuplicadoExpt;
import avaliacao.luiz.infra.*;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    static String[] opcoes = {"Cadastrar cliente", "Cadastrar Produto", "Vender Produto", "Listar clientes", "Sair"};
    static IMetodos[] metodos = {Main::cadastrarCliente, Main::cadastrarProduto, Main::venderProduto, Main::listarClientes};

    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConn(); final Utils scan = new Utils()) {
            while (true) {
                // System.out.println(conn);
                System.out.println("Bem-vindo a aplicação de vendas");
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

    private static void cadastrarCliente(Connection conn, Utils scanner) {
        var clienteConn = new ClienteDao(conn);
        System.out.println("\nO cliente é pessoa física ou jurídica?\n1- Física\n2- Jurídica");
        int opt = scanner.lerOption("Digite uma opção: ", 1, 2);
        String nome = scanner.lerString("Nome do cliente: ", "Nome inválido");
        String telefone = scanner.lerOnzeDigitos("Telefone do cliente (Apenas números): ", "Telefone inválido");

        try {
            if (clienteConn.selectField("telefone", telefone).isPresent()) throw new RegistroDuplicadoExpt("telefone");
        } catch (RegistroDuplicadoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        Cliente cliente;
        if (opt == 0) {
            String cpf = scanner.lerOnzeDigitos("CPF do cliente (Apenas números): ", "CPF inválido");
            try {
                if (clienteConn.selectField("cpf", cpf).isPresent()) throw new RegistroDuplicadoExpt("cpf");
            } catch (RegistroDuplicadoExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            LocalDate dataNascimento = scanner.lerData("Data de nascimento (xx/xx/xxxx): ");
            cliente = new PessoaFisica(nome, telefone, cpf, dataNascimento);
        } else {
            String cnpj = scanner.lerCnpj("CNPJ do cliente (Apenas números): ", "CNPJ inválido");
            try {
                if (clienteConn.selectField("cnpj", cnpj).isPresent()) throw new RegistroDuplicadoExpt("cnpj");
            } catch (RegistroDuplicadoExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            String nomeEmpresa = scanner.lerString("Nome da empresa: ", "Nome inválido");
            cliente = new Empresa(nome, telefone, cnpj, nomeEmpresa);
        }
        clienteConn.insert(cliente);
    }

    private static void cadastrarProduto(Connection conn, Utils scanner) {
        var produtoConn = new ProdutoDao(conn);
        String nome = scanner.lerString("\nNome do produto: ", "Nome inválido");
        try {
            if (produtoConn.select(nome).isPresent()) throw new RegistroDuplicadoExpt("nome");
        } catch (RegistroDuplicadoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        double preco = scanner.lerDouble("Preço do produto: ");
        try {
            if (preco <= 0) throw new EntradaInvalidaExpt("Preço");
        } catch (EntradaInvalidaExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        double quantidade = scanner.lerDouble("Quantidade do produto: ");
        try {
            if (quantidade <= 0) throw new EntradaInvalidaExpt("Quantidade");
        } catch (EntradaInvalidaExpt e) {
            System.out.println(e.getMessage());
            return;
        }
        produtoConn.insert(new Produto(nome, preco, quantidade));
    }

    private static void venderProduto(Connection conn, Utils scanner) {
        var clienteconn = new ClienteDao(conn);
        int clienteId = scanner.lerInt("Digite o id do cliente: ");
        Optional<Cliente> c = clienteconn.select(clienteId);
        try {
            if (c.isEmpty()) throw new NaoEncontradoExpt("cliente");
        } catch (NaoEncontradoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        var produtoconn = new ProdutoDao(conn);
        List<Produto> produtos = produtoconn.select();
        List<ItemVenda> carrinho = new ArrayList<>();
        var compraConn = new CompraDao(conn);
        int idCompra = compraConn.insert(new Compra(c.get().getId()));
        while (true) {
            List<Produto> produtosFiltrados = produtos.stream().filter(p -> p.getQuantidade() > 0).toList();
            try {
                if (produtosFiltrados.isEmpty()) throw new NaoEncontradoExpt("Produtos");
            } catch (NaoEncontradoExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            scanner.mostraArrayFormatado(produtosFiltrados);
            int indexProduto = scanner.lerOption("Selecione o produto: ", 1, produtos.size(), "Produto selecionado inválido");
            Produto p = produtos.get(indexProduto);
            double quantidade = scanner.lerOption("Selecione a quantidade de " + p.getNome() + ": ", 1, (int) p.getQuantidade(), "Quantidade inválida") + 1.0;
            System.out.printf("%.0f %s adicionados ao carrinho%n%n", quantidade, p.getNome());
            produtoconn.updateQuantidade(p, p.getQuantidade() - quantidade);
            p.setQuantidade(p.getQuantidade() - quantidade);
            carrinho.add(new ItemVenda(p.getId(), idCompra, p.getPreco(), quantidade));
            int continuar = scanner.lerOption("Deseja adicionar mais produtos no carrinho?\n1- Sim\n2- Não\nOpção: ", 1, 2) + 1;
            if (continuar == 2) break;
        }
        System.out.println("----- Fechamento da conta -----");
        System.out.println("Cliente: " + c.get());
        scanner.mostraArrayFormatado(carrinho);
        double total = carrinho.stream().reduce(0d, (acc, p) -> acc + (p.getQuantidade() * p.getPreco()), Double::sum);

        int optPagamento = scanner.lerOption("1- Boleto\n2- Cartão de crédito\n3- Pix\nOpção: ", 1, 3) + 1;
        double valorPago;
        String tipoPagamento = "";
        switch (optPagamento) {
            case 1 -> {
                valorPago = new MetodoPagamento(new Boleto()).pagar(total);
                tipoPagamento = "BOLETO";
            }
            case 2 -> {
                valorPago = new MetodoPagamento(new CartaoCredito()).pagar(total);
                tipoPagamento = "CARTAO";
            }
            case 3 -> {
                valorPago = new MetodoPagamento(new Pix()).pagar(total);
                tipoPagamento = "PIX";
            }
            default -> throw new EntradaInvalidaExpt("Opção Pagamento " + optPagamento);
        }

        var itemVendaConn = new ItemVendaDao(conn);
        carrinho.forEach(itemVendaConn::insert);
        int idPagamento = new PagamentoDao(conn).insert(new Pagamento(tipoPagamento, idCompra, valorPago));
        compraConn.updateIdPagamento(idCompra, idPagamento);
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