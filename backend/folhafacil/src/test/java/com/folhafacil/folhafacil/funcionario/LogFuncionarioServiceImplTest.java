/*
package com.folhafacil.folhafacil.service.Log.Funcionario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.folhafacil.folhafacil.dto.Log.Funcionario.TipoLogFuncionario;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioCustomRepository;
import com.folhafacil.folhafacil.repository.Log.Funcionario.LogFuncionarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

public class LogFuncionarioServiceImplTest {

    private LogFuncionarioRepository logFuncionarioRepository;
    private LogFuncionarioCustomRepository logFuncionarioCustomRepository;
    private LogFuncionarioServiceImpl service;

    @BeforeEach
    public void setup() {
        logFuncionarioRepository = mock(LogFuncionarioRepository.class);
        logFuncionarioCustomRepository = mock(LogFuncionarioCustomRepository.class);
        service = new LogFuncionarioServiceImpl(logFuncionarioRepository, logFuncionarioCustomRepository);
    }

    @Test
    public void testGerarLogCriado() {
        String idResp = "1";
        String idManip = "2";

        service.gerarLogCriado(idResp, idManip);

        ArgumentCaptor<LogFuncionario> captor = ArgumentCaptor.forClass(LogFuncionario.class);
        verify(logFuncionarioRepository).save(captor.capture());

        LogFuncionario log = captor.getValue();
        assertEquals(TipoLogFuncionario.CRIADO, log.getTipo());
        assertEquals(idResp, log.getIdResponsavel().getId());
        assertEquals(idManip, log.getIdManipulado().getId());
        assertTrue(log.getMensagem().contains("criou o usuário"));
        assertNotNull(log.getData());
    }

    @Test
    public void testGerarLogEditado() {
        String idResp = "3";
        String idManip = "4";

        service.gerarLogEditado(idResp, idManip);

        ArgumentCaptor<LogFuncionario> captor = ArgumentCaptor.forClass(LogFuncionario.class);
        verify(logFuncionarioRepository).save(captor.capture());

        LogFuncionario log = captor.getValue();
        assertEquals(TipoLogFuncionario.ALTERADO, log.getTipo());
        assertEquals(idResp, log.getIdResponsavel().getId());
        assertEquals(idManip, log.getIdManipulado().getId());
        assertTrue(log.getMensagem().contains("editou o usuário"));
    }

    @Test
    public void testGerarLogHabilitado() {
        String idResp = "5";
        String idManip = "6";

        service.gerarLogHabilitado(idResp, idManip);

        ArgumentCaptor<LogFuncionario> captor = ArgumentCaptor.forClass(LogFuncionario.class);
        verify(logFuncionarioRepository).save(captor.capture());

        LogFuncionario log = captor.getValue();
        assertEquals(TipoLogFuncionario.HABILITADO, log.getTipo());
        assertEquals(idResp, log.getIdResponsavel().getId());
        assertEquals(idManip, log.getIdManipulado().getId());
        assertTrue(log.getMensagem().contains("habilitou o usuário"));
    }

    @Test
    public void testGerarLogDesativado() {
        String idResp = "7";
        String idManip = "8";

        service.gerarLogDesativado(idResp, idManip);

        ArgumentCaptor<LogFuncionario> captor = ArgumentCaptor.forClass(LogFuncionario.class);
        verify(logFuncionarioRepository).save(captor.capture());

        LogFuncionario log = captor.getValue();
        assertEquals(TipoLogFuncionario.DESABILITADO, log.getTipo());
        assertEquals(idResp, log.getIdResponsavel().getId());
        assertEquals(idManip, log.getIdManipulado().getId());
        assertTrue(log.getMensagem().contains("desabilitou o usuário"));
    }
}
*/