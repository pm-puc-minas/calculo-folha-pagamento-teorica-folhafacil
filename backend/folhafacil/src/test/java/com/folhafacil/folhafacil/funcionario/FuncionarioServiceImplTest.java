/*
package com.folhafacil.folhafacil.service.Funcionario;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioServiceImplTest {

    private FuncionarioRepository funcionarioRepository;
    private KeycloakService keycloakService;
    private LogFuncionarioServiceImpl logFuncionarioServiceImpl;
    private FuncionarioServiceImpl funcionarioService;
    private Jwt jwt;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        keycloakService = mock(KeycloakService.class);
        logFuncionarioServiceImpl = mock(LogFuncionarioServiceImpl.class);
        funcionarioService = new FuncionarioServiceImpl(funcionarioRepository, keycloakService, logFuncionarioServiceImpl);
        jwt = mock(Jwt.class);
    }

    @Test
    void testSalvarNovoFuncionario() {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setNome("Joao Silva");
        dto.setEmail("joao@empresa.com");
        dto.setCargo("Analista");
        dto.setBeneficios(List.of());
        dto.setId(null);

        when(keycloakService.criarUsuario(any(), any(), any(), any(), any(), any())).thenReturn("uid123");
        when(keycloakService.recuperarUID(jwt)).thenReturn("adminUID");

        funcionarioService.salvar(dto, jwt);

        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
        verify(logFuncionarioServiceImpl, times(1))
                .gerarLogCriado(eq("adminUID"), eq("uid123"));
        assertEquals("uid123", dto.getId());
        assertEquals("joao.silva", dto.getUsuario());
    }

    @Test
    void testSalvarFuncionarioExistente() {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId("uid123");
        dto.setNome("Maria Souza");
        dto.setEmail("maria@empresa.com");
        dto.setCargo("Gerente");
        dto.setBeneficios(List.of());

        when(keycloakService.recuperarUID(jwt)).thenReturn("adminUID");

        funcionarioService.salvar(dto, jwt);

        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
        verify(logFuncionarioServiceImpl, times(1))
                .gerarLogEditado(eq("adminUID"), eq("uid123"));
    }

    @Test
    void testHabilitarFuncionarioDesativado() {
        Funcionario f = new Funcionario();
        f.setId("func1");
        f.setStatus(false);

        when(funcionarioRepository.findById("func1")).thenReturn(Optional.of(f));
        when(keycloakService.recuperarUID(jwt)).thenReturn("adminUID");

        funcionarioService.habilitar("func1", jwt);

        assertTrue(f.getStatus());
        verify(funcionarioRepository).save(f);
        verify(logFuncionarioServiceImpl).gerarLogHabilitado("adminUID", "func1");
    }

    @Test
    void testHabilitarFuncionarioJaAtivo() {
        Funcionario f = new Funcionario();
        f.setId("func1");
        f.setStatus(true);
        when(funcionarioRepository.findById("func1")).thenReturn(Optional.of(f));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> funcionarioService.habilitar("func1", jwt));
        assertEquals("Conta já habilitada", ex.getMessage());
    }

    @Test
    void testDesabilitarFuncionarioAtivo() {
        Funcionario f = new Funcionario();
        f.setId("func1");
        f.setStatus(true);

        when(funcionarioRepository.findById("func1")).thenReturn(Optional.of(f));
        when(keycloakService.recuperarUID(jwt)).thenReturn("adminUID");

        funcionarioService.desabilitar("func1", jwt);

        assertFalse(f.getStatus());
        verify(funcionarioRepository).save(f);
        verify(logFuncionarioServiceImpl).gerarLogDesativado("adminUID", "func1");
    }

    @Test
    void testDesabilitarFuncionarioJaInativo() {
        Funcionario f = new Funcionario();
        f.setId("func1");
        f.setStatus(false);
        when(funcionarioRepository.findById("func1")).thenReturn(Optional.of(f));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> funcionarioService.desabilitar("func1", jwt));
        assertEquals("Conta ja desabilitada", ex.getMessage());
    }

    @Test
    void testGetINSS() {
        Funcionario f = new Funcionario();
        f.setSalarioBase(new BigDecimal("3000.00"));
        BigDecimal inss = funcionarioService.getINSS(f);
        assertTrue(inss.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testGetFGTS() {
        Funcionario f = new Funcionario();
        f.setSalarioBase(new BigDecimal("2500.00"));
        BigDecimal fgts = funcionarioService.getFGST(f);
        assertEquals(new BigDecimal("200.00"), fgts);
    }

    @Test
    void testGetIRRF() {
        Funcionario f = new Funcionario();
        f.setSalarioBase(new BigDecimal("4000.00"));
        f.setNumDependentes(0);
        BigDecimal irrf = funcionarioService.getIRRF(f);
        assertTrue(irrf.compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testValorHoraExtra() {
        Funcionario f = new Funcionario();
        f.setSalarioBase(new BigDecimal("2000.00"));
        f.setHorasDiarias(8);
        f.setDiasMensal(22);
        BigDecimal valorHora = funcionarioService.valorHoraExtra(f);
        assertEquals(new BigDecimal("11.36"), valorHora);
    }

    @Test
    void testContarDiasUteis() {
        int dias = funcionarioService.contarDiasUteis(1, 2025, false);
        assertTrue(dias >= 20 && dias <= 23);
    }
}
*/