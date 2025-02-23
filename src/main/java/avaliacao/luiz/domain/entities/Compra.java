package avaliacao.luiz.domain.entities;

import java.time.LocalDateTime;

public class Compra {
    private Produto produto;
    private Cliente cliente;
    private Pagamento pagamento;
    private int quantidade_produto;
    private LocalDateTime dataCompra;

    public Compra(Produto produto, Cliente cliente, Pagamento pagamento, int quantidade_produto) {
        this.produto = produto;
        this.cliente = cliente;
        this.pagamento = pagamento;
        this.quantidade_produto = quantidade_produto;
        this.dataCompra = LocalDateTime.now();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Compra{");
        sb.append("produto=").append(produto);
        sb.append(", cliente=").append(cliente);
        sb.append(", dataCompra=").append(dataCompra);
        sb.append('}');
        return sb.toString();
    }
}