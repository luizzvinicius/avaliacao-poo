package avaliacao.luiz.domain.entities.metodos_pagamento;

public class Pix implements FormaPagamento {
    @Override
    public double pagar(double valor) {
        System.out.printf("Pagando %.2f com Pix, desconto de 10%%. Total R$ %.2f%n%n", valor, valor * 0.9);
        return valor * 0.9;
    }
}