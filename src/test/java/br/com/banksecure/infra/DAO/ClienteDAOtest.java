package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Cliente;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.DAO.ClienteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteDAOtest {

    private ClienteDAO dao;
    private Connection connection;

    @BeforeEach
    void setup() throws Exception {
        dao = new ClienteDAO();
        connection = mock(Connection.class);

        // injeta a conexão mockada na DAO via reflexão
        var field = ClienteDAO.class.getDeclaredField("con");
        field.setAccessible(true);
        field.set(dao, connection);
    }

    @Test
    void deveSalvarClienteComSucesso() throws Exception {
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(stmt);

        Cliente cliente = new Cliente("João", "12345678901", LocalDate.of(2000, 1, 1));

        dao.save(cliente);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(stmt, times(1)).setString(1, cliente.getNome());
        verify(stmt, times(1)).setString(2, cliente.getCpf());
        verify(stmt, times(1)).setDate(eq(3), any(Date.class));
        verify(stmt, times(1)).execute();
        verify(stmt, times(1)).close();
    }

    @Test
    void deveLancarErroNoSaveQuandoSQLException() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new java.sql.SQLException());

        Cliente cliente = new Cliente("Maria", "98765432100", LocalDate.now());

        assertThrows(DadosInvalidosException.class, () -> dao.save(cliente));
    }

    @Test
    void deveRetornarListaDeClientesEmGetAll() throws Exception {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("nome")).thenReturn("João");
        when(rs.getString("cpf")).thenReturn("12345678901");
        when(rs.getDate("data_nascimento")).thenReturn(Date.valueOf("2000-01-01"));

        List<Cliente> clientes = dao.getAll();

        assertEquals(1, clientes.size());
        assertEquals("João", clientes.get(0).getNome());
    }

    @Test
    void deveLancarErroQuandoGetAllNaoEncontrarNada() throws Exception {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        assertThrows(EstruturaBancoException.class, () -> dao.getAll());
    }

    @Test
    void deveRetornarClientePeloId() throws Exception {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getLong("id")).thenReturn(10L);
        when(rs.getString("nome")).thenReturn("Pedro");
        when(rs.getString("cpf")).thenReturn("55555555555");
        when(rs.getDate("data_nascimento")).thenReturn(Date.valueOf("1999-05-05"));

        Cliente cliente = dao.getById(10);

        assertEquals("Pedro", cliente.getNome());
        assertEquals(10L, cliente.getId());
    }

    @Test
    void deveLancarErroQuandoClienteNaoExisteGetById() throws Exception {
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        assertThrows(EstruturaBancoException.class, () -> dao.getById(99));
    }

    @Test
    void deveLancarErroQuandoFalharNoGetById() throws Exception {
        when(connection.createStatement()).thenThrow(new RuntimeException());

        assertThrows(EstruturaBancoException.class, () -> dao.getById(5));
    }
}
