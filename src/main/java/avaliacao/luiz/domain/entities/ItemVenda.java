package avaliacao.luiz.domain.entities;

public class ItemVenda {
    private int id;
    private int idProduto;
    private int idCompra;
    private double preco;
    private double quantidade;

    public ItemVenda(int id, int idProduto, int idCompra, double preco, double quantidade) {
        this.id = id;
        this.idProduto = idProduto;
        this.idCompra = idCompra;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public ItemVenda(int idProduto, int idCompra, double preco, double quantidade) {
        this.idProduto = idProduto;
        this.idCompra = idCompra;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public double getPreco() {
        return preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carrinho: ");
        sb.append("idProduto ").append(idProduto).append(" | ");
        sb.append("idCompra ").append(idCompra).append(" | ");
        sb.append("Preco R$").append(preco).append(" | ");
        sb.append("Quantidade ").append(quantidade).append(" | ");
        return sb.toString();
    }
}