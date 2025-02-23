package avaliacao.luiz.domain.entities;

public class Empresa extends Cliente {
    private String cnpj;
    private String nomeResponsavel;

    public Empresa(String nome, String telefone, String cnpj, String nomeResponsavel) {
        super(nome, telefone);
        this.cnpj = cnpj;
        this.nomeResponsavel = nomeResponsavel;
    }

    @Override
    public String getIdentificador() {
        return cnpj;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Empresa {");
        sb.append("nome='").append(super.getNome()).append('\'');
        sb.append(", telefone='").append(super.getTelefone()).append('\'');
        sb.append(", cnpj='").append(cnpj).append('\'');
        sb.append(", nomeResponsavel='").append(nomeResponsavel).append('\'');
        sb.append("}");
        return sb.toString();
    }
}