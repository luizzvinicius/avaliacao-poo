package avaliacao.luiz.domain.entities.metodos_pagamento;

public class CartaoCredito implements FormaPagamento {
    @Override
    public double pagar(double valor) {
        System.out.printf("Pagando %.2f com cartão de crédito em 1x, desconto de 5%%. Total R$ %.2f%n%n", valor, valor * 0.95);
        return valor * 0.95;
    }
}