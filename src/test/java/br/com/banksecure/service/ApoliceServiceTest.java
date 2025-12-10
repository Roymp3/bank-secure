package br.com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.service.ApoliceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verifyNoInteractions;

public class ApoliceServiceTest {

    private ApoliceDAO apoliceDAO;
    private ApoliceService service;

    @BeforeEach
    void setup() {
        apoliceDAO = Mockito.mock(ApoliceDAO.class);
        service = new ApoliceService();
    }

    private Apolice criarApoliceValida() {
        return new Apolice(
                1L,
                1L,
                1L,
                new BigDecimal("1000"),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                false
        );
    }

    @Test
    void deveValidarApoliceValidaSemErro() {
        Apolice apolice = criarApoliceValida();

        assertDoesNotThrow(() -> service.validarApoliceDAO(apolice));

        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoApoliceNull() {
        assertThrows(IllegalArgumentException.class, () -> service.validarApoliceDAO(null));

        verifyNoInteractions(apoliceDAO);
    }

    @Test
    void deveLancarErroQuandoCamposObrigatoriosNull() {
        Apolice apoliceInvalida = new Apolice(
                null,
                1L,
                1L,
                new BigDecimal("1000"),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                false
        );

        assertThrows(DadosInvalidosException.class, () -> service.validarApoliceDAO(apoliceInvalida));

        verifyNoInteractions(apoliceDAO);
    }
}
