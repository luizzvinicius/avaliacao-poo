package avaliacao.luiz.domain.entities;

import java.util.List;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private double quantidade;
    private List<Compra> compras;

    public Produto(int id, String nome, double preco, double quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(String nome, double preco, double quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" | ");
        sb.append("R$ ").append(preco).append(" | " );
        sb.append("Quantidade: ").append(quantidade);
        return sb.toString();
    }
}