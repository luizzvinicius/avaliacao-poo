package avaliacao.luiz.domain.exceptions;

public class EntradaInvalidaExpt extends RuntimeException {
    public EntradaInvalidaExpt(String campo) {
        super(campo + " inválido\n");
    }
}