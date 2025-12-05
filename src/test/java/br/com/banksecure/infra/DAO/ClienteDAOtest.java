package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ClienteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOtest {

    @Mock
    private ClienteDAO clienteDAO;

    @BeforeEach
    public void setup() {
        clienteDAO = new ClienteDAO();
        clienteDAO.iniciaTabela();
    }

    @Test
    void deveInicializarCliente() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();
    }

    @Test
    void deveSalvarUmCliente() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente novoCliente = new Cliente("Flavão", "08758569578", LocalDate.of(2004, 5, 25));

        dao.save(novoCliente);
    }

    @Test
    void deveMostrarOsClientesSalvos() {
        System.out.println(clienteDAO.getAll());
    }

    @Test
    void deveRetornarQuantidadeDeClientesSalvos() {
        assertEquals(2, clienteDAO.getAll().size());
    }

    @Test
    void deveRetornarErroDadosInvalidos() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente clienteInvalido = new Cliente(null, "", "", LocalDate.now());

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            dao.save(clienteInvalido);
        });

        assertEquals("Dados invalidos: os campos do Cliente não podem ser vazios ou negativos.", e.getMessage());
    }

    @Test
    void deveRetornarCpfInvalido() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente cpfInvalido = new Cliente("Sla mano", "04geg18ewgg", LocalDate.of(2004, 5, 25));

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            dao.save(cpfInvalido);
        });

        assertEquals("CPF inválido: O cpf deve ter 11 digitos.", e.getMessage());
    }

    @Test
    void deveRetornarClienteById() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente cliente = dao.getById(1);

        assertEquals(1L, cliente.getId());
        assertEquals("Flavão", cliente.getNome());
        assertEquals("80758585472", cliente.getCpf());
        assertEquals(LocalDate.of(2004, 9, 15), cliente.getDataNascimento());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente cliente = new Cliente("Teste", "12345678910", LocalDate.of(1990, 1, 1));
        dao.save(cliente);

        int tamanhoAntes = dao.getAll().size();

        dao.delete(cliente);

        int tamanhoDepois = dao.getAll().size();

        assertEquals(tamanhoAntes - 1, tamanhoDepois);
    }

    @Test
    void deveFalharAoDeletarClienteSemId() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente cliente = new Cliente("Teste", "12345678910", LocalDate.of(1990, 1, 1));

        assertThrows(DadosInvalidosException.class, () -> dao.delete(cliente));
    }

    @Test
    void deveFalharAoDeletarClienteInexistente() {
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();

        Cliente cliente = new Cliente("Teste", "12345678910", LocalDate.of(1990, 1, 1));
        cliente.setId(9999L);

        assertThrows(DadosInvalidosException.class, () -> dao.delete(cliente));
    }
}