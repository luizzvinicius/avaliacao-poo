package avaliacao.luiz.domain.entities.metodos_pagamento;

public class Boleto implements FormaPagamento {
    @Override
    public double pagar(double valor) {
        System.out.printf("Pagando %.2f com boleto, Total R$ %.2f%n%n", valor, valor);
        return valor;
    }
}