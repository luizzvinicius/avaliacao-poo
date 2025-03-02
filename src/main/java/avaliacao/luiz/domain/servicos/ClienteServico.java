package avaliacao.luiz.domain.servicos;

import avaliacao.luiz.domain.entities.Cliente;
import avaliacao.luiz.domain.entities.Empresa;
import avaliacao.luiz.domain.entities.PessoaFisica;
import avaliacao.luiz.domain.exceptions.NaoEncontradoExpt;
import avaliacao.luiz.domain.exceptions.RegistroDuplicadoExpt;
import avaliacao.luiz.infra.ClienteDao;
import avaliacao.luiz.utils.Utils;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

public class ClienteServico {
    public static void cadastrar(Connection conn, Utils util) {
        var clienteConn = new ClienteDao(conn);
        int opt = util.lerOption("\nO cliente é pessoa física ou jurídica?\n1- Física\n2- Jurídica\nDigite uma opção: ", 1, 2);
        String nome = util.lerString("Nome do cliente: ", "Nome inválido");
        String telefone = util.lerOnzeDigitos("Telefone do cliente (Apenas números): ", "Telefone inválido");

        try {
            if (clienteConn.selectField("telefone", telefone).isPresent()) throw new RegistroDuplicadoExpt("telefone");
        } catch (RegistroDuplicadoExpt e) {
            System.out.println(e.getMessage());
            return;
        }

        Cliente cliente;
        if (opt == 0) {
            String cpf = util.lerOnzeDigitos("CPF do cliente (Apenas números): ", "CPF inválido");
            try {
                if (clienteConn.selectField("cpf", cpf).isPresent()) throw new RegistroDuplicadoExpt("cpf");
            } catch (RegistroDuplicadoExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            LocalDate dataNascimento = util.lerData("Data de nascimento (xx/xx/xxxx): ");
            cliente = new PessoaFisica(nome, telefone, cpf, dataNascimento);
        } else {
            String cnpj = util.lerCnpj("CNPJ do cliente (Apenas números): ", "CNPJ inválido");
            try {
                if (clienteConn.selectField("cnpj", cnpj).isPresent()) throw new RegistroDuplicadoExpt("cnpj");
            } catch (RegistroDuplicadoExpt e) {
                System.out.println(e.getMessage());
                return;
            }
            String nomeEmpresa = util.lerString("Nome da empresa: ", "Nome inválido");
            cliente = new Empresa(nome, telefone, cnpj, nomeEmpresa);
        }
        clienteConn.insert(cliente);
    }

    public static Cliente select(Connection conn, Utils scanner) {
        int clienteId = scanner.lerInt("Digite o id do cliente: ");
        Optional<Cliente> c = new ClienteDao(conn).select(clienteId);

        if (c.isEmpty()) throw new NaoEncontradoExpt("Cliente");

        return c.get();
    }

    public static void listar(Connection conn, Utils util) {
        var clientes = new ClienteDao(conn).selectAll();
        if (clientes.isEmpty()) {
            System.out.println("Sem clientes cadastrados\n");
            return;
        }
        util.mostraArrayFormatado(clientes);
    }
}