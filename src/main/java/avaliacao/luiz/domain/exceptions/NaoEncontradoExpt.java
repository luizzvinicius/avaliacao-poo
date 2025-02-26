package avaliacao.luiz.domain.exceptions;

public class NaoEncontradoExpt extends RuntimeException {
    public NaoEncontradoExpt(String entity) {
        super(entity + " não encontrado\n");
    }
}