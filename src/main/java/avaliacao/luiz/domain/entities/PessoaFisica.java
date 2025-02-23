package avaliacao.luiz.domain.entities;

import java.time.LocalDate;

public class PessoaFisica extends Cliente {
    private String cpf;
    private LocalDate dataNascimento;

    public PessoaFisica(int id, String nome, String telefone, String cpf, LocalDate dataNascimento) {
        super(id, nome, telefone);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String getIdentificador() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PessoaFisica{");
        sb.append("cpf='").append(cpf).append('\'');
        sb.append(", dataNascimento=").append(dataNascimento);
        sb.append('}');
        return sb.toString();
    }
}