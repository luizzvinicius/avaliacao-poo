package avaliacao.luiz.domain.entities;

import java.util.List;

public abstract class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private List<Compra> compras;

    protected Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public abstract String getIdentificador();

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}