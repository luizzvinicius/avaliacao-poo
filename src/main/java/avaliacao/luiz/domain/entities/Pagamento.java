package avaliacao.luiz.domain.entities;

public class Pagamento {
    private int id;
    private String tipo;
    private int idCompra;
    private double total;

    public Pagamento(String tipo, int idCompra, double total) {
        this.tipo = tipo;
        this.idCompra = idCompra;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getTotal() {
        return total;
    }

    public int getIdCompra() {
        return idCompra;
    }
}