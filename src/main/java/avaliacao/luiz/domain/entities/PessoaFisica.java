package avaliacao.luiz.domain.entities;

import java.time.LocalDate;

public class PessoaFisica extends Cliente {
    private int id;
    private String cpf;
    private LocalDate dataNascimento;

    public PessoaFisica(int id, String nome, String telefone, String cpf, LocalDate dataNascimento) {
        super(id, nome, telefone);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public PessoaFisica(String nome, String telefone, String cpf, LocalDate dataNascimento) {
        super(nome, telefone);
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
        final StringBuilder sb = new StringBuilder("Pessoa Física ");
        sb.append(super.getNome()).append(" | ");
        sb.append(super.getTelefone()).append(" | ");
        sb.append("CPF").append(cpf).append(" | ");
        sb.append("data de nascimento").append(dataNascimento);
        return sb.toString();
    }
}