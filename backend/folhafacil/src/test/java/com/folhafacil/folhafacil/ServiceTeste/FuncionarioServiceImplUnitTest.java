package com.folhafacil.folhafacil.ServiceTeste;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioDTO;
import com.folhafacil.folhafacil.dto.FuncionarioBeneficio.FuncionarioBeneficioDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.mapper.FuncionarioMapper;
import com.folhafacil.folhafacil.mapper.FuncionarioBeneficioMapper;
import com.folhafacil.folhafacil.repository.Funcionario.FuncionarioRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl;
import com.folhafacil.folhafacil.service.Log.Funcionario.LogFuncionarioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceImplUnitTest {

    private FuncionarioRepository funcionarioRepository;
    private KeycloakService keycloakService;
    private LogFuncionarioServiceImpl logFuncionarioServiceImpl;
    private FuncionarioServiceImpl service;

    private Jwt token;
    private FuncionarioDTO funcionarioDTO;
    private Funcionario funcionarioEntity;
    private List<Funcionario> funcionariosList;
    private List<FuncionarioBeneficioDTO> beneficiosDTOs;
    private List<FuncionarioBeneficio> beneficiosEntities;

    @BeforeEach
    void setUp() {
        funcionarioRepository = Mockito.mock(FuncionarioRepository.class);
        keycloakService = Mockito.mock(KeycloakService.class);
        logFuncionarioServiceImpl = Mockito.mock(LogFuncionarioServiceImpl.class);

        service = new FuncionarioServiceImpl(
                funcionarioRepository,
                keycloakService,
                logFuncionarioServiceImpl
        );

        token = Mockito.mock(Jwt.class);
        when(keycloakService.recuperarUID(token)).thenReturn("admin-user");

        funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNome("João Silva");
        funcionarioDTO.setEmail("joao@email.com");
        funcionarioDTO.setCargo("CLT");
        funcionarioDTO.setSalarioBase(BigDecimal.valueOf(3000));
        funcionarioDTO.setHorasDiarias(8);
        funcionarioDTO.setDiasMensal(22);
        funcionarioDTO.setNumDependentes(1);
        funcionarioDTO.setBeneficios(Arrays.asList(new FuncionarioBeneficioDTO()));

        funcionarioEntity = new Funcionario();
        funcionarioEntity.setId("123");
        funcionarioEntity.setNome("João Silva");
        funcionarioEntity.setSalarioBase(BigDecimal.valueOf(3000));
        funcionarioEntity.setStatus(true);

        funcionariosList = Arrays.asList(funcionarioEntity);

        beneficiosDTOs = Arrays.asList(new FuncionarioBeneficioDTO() {{ setValor(BigDecimal.valueOf(200)); }});

        beneficiosEntities = Arrays.asList(new FuncionarioBeneficio() {{ setValor(BigDecimal.valueOf(200)); }});
    }

    @Test
    void deveSalvarComSucessoNovoFuncionario() {
        funcionarioDTO.setId(null);
        String uidMock = "new-uid";

        try (MockedStatic<FuncionarioMapper> mapperMock = Mockito.mockStatic(FuncionarioMapper.class);
             MockedStatic<FuncionarioBeneficioMapper> beneficioMapperMock = Mockito.mockStatic(FuncionarioBeneficioMapper.class)) {
            mapperMock.when(() -> FuncionarioMapper.toEntity(any(FuncionarioDTO.class), anyList())).thenReturn(funcionarioEntity);
            beneficioMapperMock.when(() -> FuncionarioBeneficioMapper.toEntityList(anyList(), anyString())).thenReturn(beneficiosEntities);
            when(keycloakService.criarUsuario(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(uidMock);
            when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioEntity);

            service.salvar(funcionarioDTO, token);

            verify(keycloakService, times(1)).criarUsuario(eq("João.Silva"), eq("joao@email.com"), eq("João"), eq("Silva"), eq("FolhaFacil2025"), eq("CLT"));
            verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
            verify(logFuncionarioServiceImpl, times(1)).gerarLogCriado(eq("admin-user"), eq(uidMock));
        }
    }

    @Test
    void deveSalvarComSucessoFuncionarioExistente() {
        funcionarioDTO.setId("existing-id");

        try (MockedStatic<FuncionarioMapper> mapperMock = Mockito.mockStatic(FuncionarioMapper.class);
             MockedStatic<FuncionarioBeneficioMapper> beneficioMapperMock = Mockito.mockStatic(FuncionarioBeneficioMapper.class)) {
            mapperMock.when(() -> FuncionarioMapper.toEntity(any(FuncionarioDTO.class), anyList())).thenReturn(funcionarioEntity);
            beneficioMapperMock.when(() -> FuncionarioBeneficioMapper.toEntityList(anyList(), anyString())).thenReturn(beneficiosEntities);
            when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioEntity);

            service.salvar(funcionarioDTO, token);

            verify(keycloakService, times(1)).recuperarUID(token);
            verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
            verify(logFuncionarioServiceImpl, times(1)).gerarLogEditado(eq("admin-user"), eq("existing-id"));
        }
    }

    @Test
    void deveLancarRuntimeExceptionAoSalvarFalha() {
        funcionarioDTO.setId(null);

        try (MockedStatic<FuncionarioMapper> mapperMock = Mockito.mockStatic(FuncionarioMapper.class);
             MockedStatic<FuncionarioBeneficioMapper> beneficioMapperMock = Mockito.mockStatic(FuncionarioBeneficioMapper.class)) {
            mapperMock.when(() -> FuncionarioMapper.toEntity(any(FuncionarioDTO.class), anyList())).thenReturn(funcionarioEntity);
            beneficioMapperMock.when(() -> FuncionarioBeneficioMapper.toEntityList(anyList(), anyString())).thenReturn(beneficiosEntities);
            when(keycloakService.criarUsuario(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Erro no Keycloak"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                service.salvar(funcionarioDTO, token);
            });
            assertEquals("Erro no Keycloak", exception.getMessage());
        }
    }

    @Test
    void deveHabilitarComSucesso() {
        funcionarioEntity.setStatus(false);
        when(funcionarioRepository.findById(eq("123"))).thenReturn(Optional.of(funcionarioEntity));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioEntity);

        service.habilitar("123", token);

        assertTrue(funcionarioEntity.getStatus());
        verify(funcionarioRepository, times(1)).findById(eq("123"));
        verify(funcionarioRepository, times(1)).save(funcionarioEntity);
        verify(logFuncionarioServiceImpl, times(1)).gerarLogHabilitado(eq("admin-user"), eq("123"));
    }

    @Test
    void deveLancarRuntimeExceptionAoHabilitarContaJaHabilitada() {
        funcionarioEntity.setStatus(true);
        when(funcionarioRepository.findById(eq("123"))).thenReturn(Optional.of(funcionarioEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.habilitar("123", token);
        });
        assertEquals("Conta já habilitada", exception.getMessage());
        verifyNoInteractions(logFuncionarioServiceImpl);
    }

    @Test
    void deveLancarRuntimeExceptionAoHabilitarFalha() {
        when(funcionarioRepository.findById(eq("123"))).thenThrow(new RuntimeException("Erro no repo"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.habilitar("123", token);
        });
        assertEquals("Erro no repo", exception.getMessage());
    }

    @Test
    void deveDesabilitarComSucesso() {
        funcionarioEntity.setStatus(true);
        when(funcionarioRepository.findById(eq("123"))).thenReturn(Optional.of(funcionarioEntity));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioEntity);

        service.desabilitar("123", token);

        assertFalse(funcionarioEntity.getStatus());
        verify(funcionarioRepository, times(1)).findById(eq("123"));
        verify(funcionarioRepository, times(1)).save(funcionarioEntity);
        verify(logFuncionarioServiceImpl, times(1)).gerarLogDesativado(eq("admin-user"), eq("123"));
    }

    @Test
    void deveLancarRuntimeExceptionAoDesabilitarContaJaDesabilitada() {
        funcionarioEntity.setStatus(false);
        when(funcionarioRepository.findById(eq("123"))).thenReturn(Optional.of(funcionarioEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.desabilitar("123", token);
        });
        assertEquals("Conta já desabilitada", exception.getMessage());
        verifyNoInteractions(logFuncionarioServiceImpl);
    }

    @Test
    void deveLancarRuntimeExceptionAoDesabilitarFalha() {
        when(funcionarioRepository.findById(eq("123"))).thenThrow(new RuntimeException("Erro no repo"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.desabilitar("123", token);
        });
        assertEquals("Erro no repo", exception.getMessage());
    }

    @Test
    void deveFindByStatusComSucesso() {
        when(funcionarioRepository.findByStatus(eq(true))).thenReturn(funcionariosList);

        List<Funcionario> result = service.findByStatus(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(funcionarioRepository, times(1)).findByStatus(eq(true));
    }

    @Test
    void deveCalcularTotalValorBeneficiosComSucesso() {
        funcionarioEntity.setBeneficios(beneficiosEntities);

        BigDecimal result = service.getTotalValorBeneficios(funcionarioEntity);

        assertEquals(0, BigDecimal.valueOf(200).compareTo(result));
    }

    @Test
    void deveCalcularTotalValorBeneficiosZeroSemBeneficios() {
        funcionarioEntity.setBeneficios(List.of());

        BigDecimal result = service.getTotalValorBeneficios(funcionarioEntity);

        assertEquals(0, BigDecimal.ZERO.compareTo(result));
    }

    @Test
    void deveCalcularValorHoraExtraComSucesso() {
        funcionarioEntity.setHorasDiarias(8);
        funcionarioEntity.setDiasMensal(22);
        funcionarioEntity.setSalarioBase(BigDecimal.valueOf(3000));

        BigDecimal result = service.calcularValorHoraExtra(funcionarioEntity);

        assertEquals(0, BigDecimal.valueOf(17.05).compareTo(result));
    }

    @Test
    void deveCalcularINSSComSucesso() {
        funcionarioEntity.setSalarioBase(BigDecimal.valueOf(3000));

        BigDecimal result = service.getINSS(funcionarioEntity);

        assertEquals(0, BigDecimal.valueOf(258.82).compareTo(result));
    }

   @Test
    void deveCalcularFGSTComSucesso() {
        funcionarioEntity.setSalarioBase(BigDecimal.valueOf(3000));

        BigDecimal result = service.getFGTS(funcionarioEntity);

        assertEquals(0, BigDecimal.valueOf(240.00).compareTo(result));
    }

    @Test
    void deveCalcularIRRFComSucesso() {
        funcionarioEntity.setSalarioBase(BigDecimal.valueOf(3000));
        funcionarioEntity.setNumDependentes(1);

        BigDecimal result = service.getIRRF(funcionarioEntity);

        assertEquals(0, BigDecimal.valueOf(32.97).compareTo(result));
    }

    @Test
    void deveContarDiasUteisComSucesso() {
        int result = service.contarDiasUteis(1, 2024, false);
        assertEquals(23, result);

        int resultComSab = service.contarDiasUteis(1, 2024, true);
        assertEquals(27, resultComSab);
    }

    @Test
    void deveLancarExceptionAoContarDiasUteisMesInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.contarDiasUteis(13, 2024, false);
        });
    }
}