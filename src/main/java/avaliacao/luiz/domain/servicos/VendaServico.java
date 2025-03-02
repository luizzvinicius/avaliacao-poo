package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.*;
import avaliacao.luiz.domain.entities.metodos_pagamento.Boleto;
import avaliacao.luiz.domain.entities.metodos_pagamento.CartaoCredito;
import avaliacao.luiz.domain.entities.metodos_pagamento.MetodoPagamento;
import avaliacao.luiz.domain.entities.metodos_pagamento.Pix;
import avaliacao.luiz.domain.exceptions.EntradaInvalidaExpt;
import avaliacao.luiz.domain.exceptions.NaoEncontradoExpt;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class VendaServico {
    public static void venderProduto(Connection conn, Utils utils) {
        Cliente c;
        try {
            c = ClienteServico.select(conn, utils);
        } catch (NaoEncontradoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        List<Produto> produtos = ProdutoServico.select(conn);
        List<ItemVenda> carrinho = new ArrayList<>();
        int idCompra = CompraServico.insert(conn, new Compra(c.getId()));
        while (true) {
            List<Produto> produtosFiltrados = produtos.stream().filter(p -> p.getQuantidade() > 0).toList();
            try {
                if (produtosFiltrados.isEmpty()) throw new NaoEncontradoExpt("Produtos");
            } catch (NaoEncontradoExpt e) {
                System.out.println(e.getMessage());
                break;
            }
            utils.mostraArrayFormatado(produtosFiltrados);
            int indexProduto = utils.lerOption("Selecione o produto: ", 1, produtos.size(), "Produto selecionado inválido");
            Produto p = produtos.get(indexProduto);
            double quantidade = utils.lerOption("Selecione a quantidade de " + p.getNome() + ": ", 1, (int) p.getQuantidade(), "Quantidade inválida") + 1.0;
            System.out.printf("%.0f %s adicionados ao carrinho%n%n", quantidade, p.getNome());
            ProdutoServico.updateQuantidade(conn, p, p.getQuantidade() - quantidade);
            p.setQuantidade(p.getQuantidade() - quantidade);
            carrinho.add(new ItemVenda(p.getId(), idCompra, p.getPreco(), quantidade));
            int continuar = utils.lerOption("Deseja adicionar mais produtos no carrinho?\n1- Sim\n2- Não\nOpção: ", 1, 2) + 1;
            if (continuar == 2) break;
        }

        if (carrinho.isEmpty()) {
            return;
        }
        fechamentoConta(conn, utils, c, carrinho, idCompra);
    }

    private static void fechamentoConta(Connection conn, Utils utils, Cliente c, List<ItemVenda> carrinho, int idCompra) {
        System.out.println("----- Fechamento da conta -----\nCliente: " + c.getNome());
        utils.mostraArrayFormatado(carrinho);
        double total = carrinho.stream().reduce(0d, (acc, p) -> acc + (p.getQuantidade() * p.getPreco()), Double::sum);

        int optPagamento = utils.lerOption("1- Boleto\n2- Cartão de crédito\n3- Pix\nOpção: ", 1, 3) + 1;
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

        carrinho.forEach(i -> ItemVendaServico.insert(conn, i));
        int idPagamento = PagamentoServico.insert(conn, new Pagamento(tipoPagamento, idCompra, valorPago));
        CompraServico.updateIdPagamento(conn, idCompra, idPagamento);
    }
}