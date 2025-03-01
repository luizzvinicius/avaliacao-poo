package domain.servicos;

import avaliacao.luiz.domain.entities.Cliente;
import avaliacao.luiz.domain.entities.Empresa;
import avaliacao.luiz.domain.entities.PessoaFisica;
import avaliacao.luiz.domain.exceptions.RegistroDuplicadoExpt;
import avaliacao.luiz.domain.servicos.ClienteServico;
import avaliacao.luiz.infra.ClienteDao;
import avaliacao.luiz.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServicoTest {
    @Mock
    private Connection conn;

    @Mock
    private Utils util;

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private PreparedStatement st;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ClienteServico clienteServico;

    @Test()
    @DisplayName("Deve cadastrar pessoa fisica com sucesso")
    void cadastrarPessoaFisicaSucesso() throws SQLException {
//        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Cadastrar Cliente
//        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Escolhe Pessoa Física
//        when(util.lerString(anyString(), anyString())).thenReturn("teste pessoa fisica"); // ler nome
//        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("11987654321");
//        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("12345678901"); // ler cpf

//        when(conn.prepareStatement(anyString())).thenReturn(st);
//        when(st.executeUpdate()).thenReturn(1); // linhas afetadas
//        when(st.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(true);

        ClienteServico.cadastrar(conn, util);
        clienteDao.insert(any(Cliente.class));
        verify(clienteDao, never()).insert(any(Cliente.class));
    }

    @Test
    void naoDeveCadastrarComTelefoneDuplicado() throws SQLException {
//        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Cadastrar Cliente
//        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Escolhe Pessoa Física
//        when(util.lerString(anyString(), anyString())).thenReturn("teste pessoa fisica"); // ler nome
//        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("11987654321");
        when(clienteDao.selectField("telefone", "82994877584")).thenThrow(new RegistroDuplicadoExpt("telefone"));

//        when(conn.prepareStatement(anyString())).thenReturn(st);
//        when(st.executeQuery()).thenReturn(resultSet);
        ClienteServico.cadastrar(conn, util);

        var expt = assertThrows(RegistroDuplicadoExpt.class, () -> {
            clienteDao.selectField("telefone", "82994877584");
        });

        assertEquals("telefone já cadastrado\n", expt.getMessage());
        verify(clienteDao, never()).insert(any(Cliente.class));
    }

    @Test
    void deveCadastrarPessoaJuridicaComSucesso() throws SQLException {
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Cadastrar Cliente
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(2); // Escolhe Empresa
        when(util.lerString(anyString(), anyString())).thenReturn("teste empresa"); // ler nome
        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("11987654321"); // ler telefone
        when(util.lerCnpj(anyString(), anyString())).thenReturn("12345678000199"); // ler CNPJ
        when(util.lerString(anyString(), anyString())).thenReturn("XYZ Ltda"); // ler nome empresa

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);

        ClienteServico.cadastrar(conn, util);
        clienteDao.insert(any(Cliente.class));
        verify(st, times(1)).executeUpdate();
        verify(clienteDao, never()).insert(any(Cliente.class));
    }

    @Test
    @DisplayName("Não deve cadastrar cliente com CPF duplicado")
    void naoCadastrarPfCPFDuplicado() throws SQLException {
        String cpf = "12345678901";
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Cadastrar Cliente
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Escolhe Pessoa Física
        when(util.lerString(anyString(), anyString())).thenReturn("teste pessoa fisica"); // ler nome
        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("11987654321"); // ler telefone
        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn(cpf); // ler cpf

        when(clienteDao.selectField("cpf", cpf)).thenThrow(new RegistroDuplicadoExpt("cpf"));

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);
        ClienteServico.cadastrar(conn, util);

        var expt = assertThrows(RegistroDuplicadoExpt.class, () -> {
            clienteDao.selectField("cpf", cpf);
        });

        assertEquals("cpf já cadastrado\n", expt.getMessage());
        verify(clienteDao, never()).insert(any(Cliente.class));
    }

    @Test
    @DisplayName("Não deve cadastrar Empresa com CNPJ duplicado")
    void naoCadastrarEmpresaCNPJDuplicado() throws SQLException {
        String cnpj = "123456789016137";
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(1); // Cadastrar Cliente
        when(util.lerOption(anyString(), anyInt(), anyInt())).thenReturn(2); // Escolhe Empresa
        when(util.lerString(anyString(), anyString())).thenReturn("teste empresa"); // ler nome
        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn("11987654321"); // ler telefone
        when(util.lerOnzeDigitos(anyString(), anyString())).thenReturn(cnpj); // ler cnpj

        when(clienteDao.selectField("cnpj", cnpj)).thenThrow(new RegistroDuplicadoExpt("cnpj"));

        when(conn.prepareStatement(anyString())).thenReturn(st);
        when(st.executeQuery()).thenReturn(resultSet);
        ClienteServico.cadastrar(conn, util);

        var expt = assertThrows(RegistroDuplicadoExpt.class, () -> {
            clienteDao.selectField("cnpj", cnpj);
        });

        assertEquals("cnpj já cadastrado\n", expt.getMessage());
        verify(clienteDao, never()).insert(any(Cliente.class));
    }

    @Test
    void deveRetornarClienteQuandoEncontrado() throws SQLException {
        Cliente clienteEsperado = new PessoaFisica("Maria", "11999999999", "12345678900", LocalDate.now());

        when(clienteDao.select(anyInt())).thenReturn(Optional.of(clienteEsperado));
        mockStatic(ClienteServico.class);
        when(ClienteServico.select(conn, util)).thenReturn(clienteEsperado);

        ClienteServico.select(conn, util);
        Optional<Cliente> resultado = clienteDao.select(anyInt());

        assertTrue(resultado.isPresent());
        assertEquals(clienteEsperado, resultado.get());
    }

    @Test
    void deveListarClientesCorretamente() {
        List<Cliente> clientes = List.of(
                new PessoaFisica("Ana", "11987654321", "11122233344", LocalDate.now()),
                new Empresa("Tech Ltda", "1133334444", "12345678000199", "Tech Company")
        );

        when(clienteDao.selectAll()).thenReturn(clientes);

        clienteDao.selectAll();

        verify(clienteDao, times(1)).selectAll();
    }

    @Test
    @DisplayName("selecionar Cliente deve retornar optional.empty não encontra cliente")
    void deveExibirMensagemErroQuandoClienteNaoEncontrado() throws SQLException {
        Cliente cliente = new PessoaFisica(94, "Ana", "11987654321", "11122233344", LocalDate.now());

        mockStatic(ClienteServico.class);
        when(ClienteServico.select(conn, util)).thenReturn(cliente);

        Cliente resultado = ClienteServico.select(conn, util);

        assertNotNull(resultado);
    }
}