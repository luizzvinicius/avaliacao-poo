package avaliacao.luiz.domain.entities.metodos_pagamento;

public class MetodoPagamento {
    public class CartaoCredito implements IMetodoPagamento {
        public static double pagar(double valor) {
            System.out.printf("Pagando %.2f com cartão de crédito em 1x, desconto de 5%%. Total R$ %.2f%n%n", valor, valor * 0.95);
            return valor * 0.95;
        }
    }

    public static class Boleto implements IMetodoPagamento {
        public static double pagar(double valor) {
            System.out.printf("Pagando %.2f com boleto, Total R$ %.2f%n%n", valor, valor);
            return valor;
        }
    }

    public static class Pix implements IMetodoPagamento {
        public static double pagar(double valor) {
            System.out.printf("Pagando %.2f com Pix, desconto de 10%%. Total R$ %.2f%n%n", valor, valor * 0.9);
            return valor * 0.9;
        }
    }
}