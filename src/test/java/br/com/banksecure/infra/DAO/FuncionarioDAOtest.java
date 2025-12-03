package br.com.banksecure.infra.DAO;

import com.banksecure.domain.Funcionario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioDAOtest {

    @Test
    void deveAlterarASenhaDoFuncionario(){
        Funcionario real = new Funcionario(1L, "usuarioTeste", "senhaAntiga");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setSenha("senhaNova");
        spyFuncionario.setSenha("senhaFinal");

        spyFuncionario.setUsuario("usuarioAlterado");

        verify(spyFuncionario, atLeast(2)).setSenha(anyString());
        verify(spyFuncionario,atLeast(1)).setUsuario(anyString());

        assertEquals("senhaFinal", spyFuncionario.getSenha());
        assertEquals("usuarioAlterado", spyFuncionario.getUsuario());

    }

    @Test

    void deveAlterarOUsuarioDoFuncionario(){
        Funcionario real = new Funcionario(2L, "usuarioOriginal", "senha123");
        Funcionario spyFuncionario = spy(real);

        spyFuncionario.setUsuario("usuarioModificado");
        spyFuncionario.setUsuario("usuarioFinal");

        spyFuncionario.setSenha("novaSenha");

        verify(spyFuncionario, atLeast(2)).setUsuario(anyString());
        verify(spyFuncionario, atLeast(1)).setSenha(anyString());

        assertEquals("usuarioFinal", spyFuncionario.getUsuario());
        assertEquals("novaSenha", spyFuncionario.getSenha());
    }

}
