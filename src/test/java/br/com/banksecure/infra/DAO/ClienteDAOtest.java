package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ClienteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOtest {

    private ClienteDAO clienteDAO;

    @BeforeEach
    public void setup(){
        clienteDAO = new ClienteDAO();
        clienteDAO.iniciaTabela();
    }

    @Test
    void deveInicializarCliente(){
        ClienteDAO dao = new ClienteDAO();
        dao.iniciaTabela();
    }

    @Test
    void deveSalvarUmCliente(){
        Cliente novoCliente = new Cliente(1L,"Flavão","08758569578", LocalDate.of(2004, 5, 25));
        ClienteDAO dao = new ClienteDAO();
        dao.save(novoCliente);
    }

    @Test
    void deveMostrarOsClientesSalvos() {
        System.out.println(clienteDAO.getAll());
    }


    @Test
    void deveRetornarQuantidadeDeClientesSalvos(){
        assertEquals(2, clienteDAO.getAll().size());
    }


    @Test
    void deveRetornarErroDadosInvalidos() {
        ClienteDAO dao = new ClienteDAO();

        Cliente clienteInvalido = new Cliente(null,"", "", LocalDate.now());

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            dao.save(clienteInvalido);
        });

        assertEquals("Dados invalidos: os campos do Cliente não podem ser vazios ou negativos.", e.getMessage());
    }

    @Test
    void deveRetornarCpfInvalido() {
        ClienteDAO dao = new ClienteDAO();

        Cliente cpfInvalido = new Cliente(1L,"Flavão", "04geg18ewgg", LocalDate.of(2004, 5, 25));

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            dao.save(cpfInvalido);
        });

        assertEquals("CPF inválido: O cpf deve ter 11 digitos.", e.getMessage());
    }


}
