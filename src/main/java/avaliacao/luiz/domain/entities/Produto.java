package avaliacao.luiz.domain.entities;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private double quantidade;

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

    public double getPreco() {
        return preco;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(nome).append(" | ");
        sb.append("R$ ").append(preco).append(" | ");
        sb.append("Quantidade: ").append(quantidade);
        return sb.toString();
    }
}