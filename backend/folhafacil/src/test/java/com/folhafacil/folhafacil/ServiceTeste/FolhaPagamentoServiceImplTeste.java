package com.folhafacil.folhafacil.ServiceTeste;

import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFolhaPagamento;
import com.folhafacil.folhafacil.entity.LogSubFolhaPagamento;
import com.folhafacil.folhafacil.entity.HoraExtra;
import com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FolhaPagamentoServiceImplUnitTest {

    private FuncionarioServiceImpl funcionarioServiceImpl;
    private HoraExtraServiceImpl horaExtraServiceImpl;
    private LogFolhaPagamentoServiceImpl logFolhaPagamentoServiceImpl;
    private KeycloakService keycloakService;
    private FolhaPagamentoRepository folhaPagamentoRepository;
    private LogSubFolhaPagamentoServiceImpl logSubFolhaPagamentoServiceImpl;
    private FolhaPagamentoServiceImpl service;

    private Jwt token;
    private List<Funcionario> funcionariosList;
    private Funcionario funcionario;
    private FolhaPagamento folhaPagamento;
    private LogFolhaPagamento logFolhaPagamento;
    private LocalDate dataInicio;

    @BeforeEach
    void setUp() {
        funcionarioServiceImpl = Mockito.mock(FuncionarioServiceImpl.class);
        horaExtraServiceImpl = Mockito.mock(HoraExtraServiceImpl.class);
        logFolhaPagamentoServiceImpl = Mockito.mock(LogFolhaPagamentoServiceImpl.class);
        keycloakService = Mockito.mock(KeycloakService.class);
        folhaPagamentoRepository = Mockito.mock(FolhaPagamentoRepository.class);
        logSubFolhaPagamentoServiceImpl = Mockito.mock(LogSubFolhaPagamentoServiceImpl.class);

        service = new FolhaPagamentoServiceImpl(
                funcionarioServiceImpl,
                horaExtraServiceImpl,
                logFolhaPagamentoServiceImpl,
                keycloakService,
                folhaPagamentoRepository,
                logSubFolhaPagamentoServiceImpl
        );

        token = Mockito.mock(Jwt.class);
        when(keycloakService.recuperarUID(token)).thenReturn("user-id");

        dataInicio = LocalDate.of(2025, 11, 1);

        funcionario = new Funcionario();
        funcionario.setId("1");
        funcionario.setSalarioBase(BigDecimal.valueOf(1000));
        funcionario.setStatus(true);
        funcionario.setCargo("CLT");

        funcionariosList = Arrays.asList(funcionario);

        folhaPagamento = new FolhaPagamento();
        folhaPagamento.setId(1L);
        folhaPagamento.setStatus(StatusFolhaPagamento.PENDENTE);

        logFolhaPagamento = new LogFolhaPagamento();
        logFolhaPagamento.setId(1L);

        doAnswer(invocation -> {
            FolhaPagamento e = invocation.getArgument(0);
            e.setData(dataInicio);
            e.setStatus(StatusFolhaPagamento.PENDENTE);
            e.setINSS(BigDecimal.valueOf(100));
            e.setFGTS(BigDecimal.valueOf(80));
            e.setIRRF(BigDecimal.valueOf(50));
            e.setTotalValorImposto(BigDecimal.valueOf(230));
            e.setTotalValorBeneficios(BigDecimal.valueOf(200));
            e.setTotalHorasExtras(BigDecimal.valueOf(10));
            e.setTotalValorHorasExtras(BigDecimal.valueOf(500));
            e.setSalarioBruto(BigDecimal.valueOf(1000));
            return e;
        }).when(folhaPagamentoRepository).save(any(FolhaPagamento.class));
    }

    @Test
    void deveGerarFolhaPagamentoComSucesso() {
        when(funcionarioServiceImpl.findByStatus(eq(true))).thenReturn(funcionariosList);
        when(folhaPagamentoRepository.findByIdFuncionarioIdAndData(eq("1"), eq(dataInicio))).thenReturn(folhaPagamento);
        when(logFolhaPagamentoServiceImpl.gerarLogGeradaAtualizada(eq("user-id"), eq(dataInicio))).thenReturn(logFolhaPagamento);
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.valueOf(100));
        when(funcionarioServiceImpl.getFGST(funcionario)).thenReturn(BigDecimal.valueOf(80));
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.valueOf(200));
        when(horaExtraServiceImpl.totalHorasNoMes(eq("1"), eq(dataInicio))).thenReturn(BigDecimal.valueOf(10));
        when(funcionarioServiceImpl.valorHoraExtra(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(horaExtraServiceImpl.findByFuncionarioAndMesAno(eq("1"), eq(dataInicio))).thenReturn(List.of(new HoraExtra()));
        when(logSubFolhaPagamentoServiceImpl.gerarLogAtualizado(eq(1L), any(FolhaPagamento.class))).thenReturn(new LogSubFolhaPagamento());

        service.gerarFolhaPagamento(token);

        verify(funcionarioServiceImpl, times(1)).findByStatus(eq(true));
        verify(logFolhaPagamentoServiceImpl, times(1)).gerarLogGeradaAtualizada(eq("user-id"), eq(dataInicio));
        verify(folhaPagamentoRepository, times(1)).findByIdFuncionarioIdAndData(eq("1"), eq(dataInicio));
        verify(logSubFolhaPagamentoServiceImpl, times(1)).gerarLogAtualizado(eq(1L), any(FolhaPagamento.class));
        verify(folhaPagamentoRepository, times(1)).save(any(FolhaPagamento.class));
    }

    @Test
    void deveLancarRuntimeExceptionAoGerarFolhaPagamentoFalha() {
        when(funcionarioServiceImpl.findByStatus(eq(true))).thenThrow(new RuntimeException("Erro ao buscar funcionários"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.gerarFolhaPagamento(token);
        });
        assertEquals("Erro ao buscar funcionários", exception.getMessage());
    }

    @Test
    void deveGerarPorFuncionarioComSucesso() {
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.valueOf(100));
        when(funcionarioServiceImpl.getFGST(funcionario)).thenReturn(BigDecimal.valueOf(80));
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.valueOf(200));
        when(horaExtraServiceImpl.totalHorasNoMes(eq("1"), eq(dataInicio))).thenReturn(BigDecimal.valueOf(10));
        when(funcionarioServiceImpl.valorHoraExtra(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(horaExtraServiceImpl.findByFuncionarioAndMesAno(eq("1"), eq(dataInicio))).thenReturn(List.of(new HoraExtra()));

        FolhaPagamento e = new FolhaPagamento();
        FolhaPagamento result = service.gerarPorFuncionario(funcionario, dataInicio, e);

        assertNotNull(result);
        assertEquals(StatusFolhaPagamento.PENDENTE, result.getStatus());
        assertEquals(dataInicio, result.getData());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(result.getINSS()));
        assertEquals(0, BigDecimal.valueOf(80).compareTo(result.getFGTS()));
        assertEquals(0, BigDecimal.valueOf(50).compareTo(result.getIRRF()));
        assertEquals(0, BigDecimal.valueOf(230).compareTo(result.getTotalValorImposto()));
        assertEquals(0, BigDecimal.valueOf(200).compareTo(result.getTotalValorBeneficios()));
        assertEquals(0, BigDecimal.valueOf(10).compareTo(result.getTotalHorasExtras()));
        assertEquals(0, BigDecimal.valueOf(500).compareTo(result.getTotalValorHorasExtras()));
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(result.getSalarioBruto()));
        verify(folhaPagamentoRepository, times(1)).save(any(FolhaPagamento.class));
    }

    @Test
    void deveLancarRuntimeExceptionAoGerarPorFuncionarioFalha() {
        when(funcionarioServiceImpl.getINSS(funcionario)).thenThrow(new RuntimeException("Erro no INSS"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.gerarPorFuncionario(funcionario, dataInicio, new FolhaPagamento());
        });
        assertEquals("Erro no INSS", exception.getMessage());
    }

    @Test
    void deveGerarPorFuncionarioParaEstagiarioSemHorasExtras() {
        funcionario.setCargo("ESTAGIARIO");
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getFGST(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.ZERO);

        FolhaPagamento e = new FolhaPagamento();
        FolhaPagamento result = service.gerarPorFuncionario(funcionario, dataInicio, e);

        assertEquals(0, BigDecimal.ZERO.compareTo(result.getTotalHorasExtras()));
        assertEquals(0, BigDecimal.ZERO.compareTo(result.getTotalValorHorasExtras()));
        verify(horaExtraServiceImpl, never()).totalHorasNoMes(anyString(), any(LocalDate.class));
    }
}