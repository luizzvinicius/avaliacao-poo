package avaliacao.luiz.domain.entities;

public class Empresa extends Cliente {
    private int id;
    private String cnpj;
    private String nomeResponsavel;

    public Empresa(int id, String nome, String telefone, String cnpj, String nomeResponsavel) {
        super(id, nome, telefone);
        this.cnpj = cnpj;
        this.nomeResponsavel = nomeResponsavel;
    }
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
        final StringBuilder sb = new StringBuilder("Empresa ");
        sb.append(super.getNome()).append(" | ");
        sb.append(super.getTelefone()).append(" | ");
        sb.append("CNPJ").append(cnpj).append(" | ");
        sb.append("Reponsável").append(nomeResponsavel);
        return sb.toString();
    }
}