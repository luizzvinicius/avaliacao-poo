package avaliacao.luiz.domain.entities;

public class Pagamento {
    private int id;
    private String tipo;
    private int idCompra;
    private double total;

    public Pagamento(int id, String tipo, int idCompra, double total) {
        this.id = id;
        this.tipo = tipo;
        this.idCompra = idCompra;
        this.total = total;
    }

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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCompra() {
        return idCompra;
    }
}