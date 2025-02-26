package avaliacao.luiz.infra;

import avaliacao.luiz.domain.entities.Cliente;
import avaliacao.luiz.domain.entities.Empresa;
import avaliacao.luiz.domain.entities.PessoaFisica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDao {
    private final Connection conn;

    public ClienteDao(Connection conn) {
        this.conn = conn;
    }

    public void insert(Cliente c) {
        String sql = c instanceof PessoaFisica ?
                "INSERT INTO cliente (nome, telefone, cpf, data_nascimento) VALUES (?,?,?,?);" :
                "INSERT INTO cliente (nome, telefone, cnpj, nome_responsavel) VALUES (?,?,?,?);";

        try (var st = conn.prepareStatement(sql)) {
            st.setString(1, c.getNome());
            st.setString(2, c.getTelefone());
            st.setString(3, c.getIdentificador());
            switch (c) {
                case PessoaFisica p -> st.setDate(4, Date.valueOf(p.getDataNascimento()));
                case Empresa e -> st.setString(4, e.getNomeResponsavel());
                default -> throw new RuntimeException("Invalid type for cliente");
            }

            st.executeUpdate();
            System.out.println("Cliente inserido.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public Optional<Cliente> select(int id) {
        final String sql = "SELECT * FROM cliente WHERE id = ?";
        Optional<Cliente> c = Optional.empty();
        try (var st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            var result = st.executeQuery();
            while (result.next()) {
                var resId = result.getInt("id");
                var nome = result.getString("nome");
                if (nome == null) return c;
                var telefone = result.getString("telefone");
                var cpf = result.getString("cpf");
                if (cpf != null) {
                    var data = result.getDate("data_nascimento").toLocalDate();
                    c = Optional.of(new PessoaFisica(resId, nome, telefone, cpf, data));
                } else {
                    c = Optional.of(new Empresa(resId, nome, telefone, result.getString("cnpj"), result.getString("nome_responsavel")));
                }
            }
            System.out.println("Cliente " + c.get().getNome() + " selecionado.");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return c;
    }

    public List<Cliente> selectAll() {
        final String sql = "SELECT * FROM cliente";
        Cliente c;
        List<Cliente> clientes = new ArrayList<>();
        try (var st = conn.prepareStatement(sql)) {
            var result = st.executeQuery();
            while (result.next()) {
                var resId = result.getInt("id");
                var nome = result.getString("nome");
                var telefone = result.getString("telefone");
                var cpf = result.getString("cpf");
                if (cpf != null) {
                    var data = result.getDate("data_nascimento").toLocalDate();
                    c = new PessoaFisica(resId, nome, telefone, cpf, data);
                } else {
                    c = new Empresa(resId, nome, telefone, result.getString("cnpj"), result.getString("nome_responsavel"));
                }
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes: " + e.getMessage());
        }
        return clientes;
    }
}