package avaliacao.luiz.infra;

public class NotFoundExpt extends RuntimeException {
    public NotFoundExpt(String entity) {
        super(entity + " não encontrado");
    }
}
