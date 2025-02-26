package avaliacao.luiz.domain.exceptions;

public class RegistroDuplicadoExpt extends RuntimeException {
    public RegistroDuplicadoExpt(String campo) {
        super(campo + " já cadastrado\n");
    }
}