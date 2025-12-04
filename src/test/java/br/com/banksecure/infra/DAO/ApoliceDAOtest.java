package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.FuncionarioDAO;
import com.banksecure.infra.DAO.SeguroDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApoliceDAOtest {

    @Mock
    ApoliceDAO apoliceDAO;

    @BeforeEach
    public void setup(){
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.iniciaTabela();
        SeguroDAO seguroDAO = new SeguroDAO();
        seguroDAO.iniciaTabelas();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inicializaTabelas();
        apoliceDAO = new ApoliceDAO();
        apoliceDAO.popularRegistro();
    }

    @Test
    public void deveIniciarUmaApolice(){
        apoliceDAO = new ApoliceDAO();
        apoliceDAO.popularRegistro();
    }

    @Test
    void deveSalvarUmaApolice(){
        Apolice apolice = new Apolice(1L,1L,1L, new BigDecimal("200000"), LocalDate.now(), LocalDate.now().plusYears(1));
        apoliceDAO.save(apolice);
    }

    @Test
    void deveMostrarTodasAsApolices(){
        System.out.println(apoliceDAO.getAll());
    }

    @Test
    void deveRetornarErroComDadosInvalidos() {
        Apolice apolice = new Apolice(null, null, null, null, LocalDate.now(), LocalDate.now().plusYears(1));

        DadosInvalidosException e = assertThrows(DadosInvalidosException.class, () -> {
            apoliceDAO.save(apolice);
        });

        assertEquals("Dados da apólice incompleto", e.getMessage());
    }
}
