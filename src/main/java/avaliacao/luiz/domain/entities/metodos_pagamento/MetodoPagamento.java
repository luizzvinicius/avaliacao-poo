package avaliacao.luiz.domain.entities.metodos_pagamento;

public class MetodoPagamento {
    private final FormaPagamento pagamento;

    public MetodoPagamento(FormaPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public double pagar(double valor) {
        return pagamento.pagar(valor);
    }
}