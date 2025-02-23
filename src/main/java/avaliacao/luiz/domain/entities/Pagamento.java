package avaliacao.luiz.domain.entities;

public class Pagamento {
    private int id;
    private TipoPagamento tipo;
    private int id_compra;
    private double total;

    public Pagamento(int id, TipoPagamento tipo, int id_compra, double total) {
        this.id = id;
        this.tipo = tipo;
        this.id_compra = id_compra;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }
}

enum TipoPagamento {
    CARTAO_CREDITO("CREDITO"), BOLETO("BOLETO"), PIX("PIX");

    private final String tipo;

    TipoPagamento(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
}