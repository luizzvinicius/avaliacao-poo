package avaliacao.luiz.domain.entities;

public abstract class Cliente {
    private int id;
    private String nome;
    private String telefone;

    protected Cliente(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

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

    public String getTelefone() {
        return telefone;
    }
}