package com.folhafacil.folhafacil.ServiceTeste;

import com.folhafacil.folhafacil.dto.FolhaPagamento.StatusFolhaPagamento;
import com.folhafacil.folhafacil.entity.*;
import com.folhafacil.folhafacil.infra.utils.CollectionUtils;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoBenficioMapper;
import com.folhafacil.folhafacil.mapper.FolhaPagamentoHoraExtraMapper;
import com.folhafacil.folhafacil.repository.FolhaPagamento.FolhaPagamentoRepository;
import com.folhafacil.folhafacil.service.Funcionario.FuncionarioServiceImpl;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.LogFolhaPagamentoServiceImpl;
import com.folhafacil.folhafacil.service.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoServiceImpl;
import com.folhafacil.folhafacil.service.ServiceGenerico;
import com.folhafacil.folhafacil.service.FolhaPagamento.FolhaPagamentoServiceImpl;

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

class BeneficoServiceImplUnitTest {

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
        // Configuração inicial dos mocks e dados de teste
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

        dataInicio = LocalDate.now().withDayOfMonth(1);

        funcionario = new Funcionario();
        funcionario.setId("1");
        funcionario.setNome("João Silva");
        funcionario.setSalarioBase(BigDecimal.valueOf(1000));
        funcionario.setStatus(Funcionario.HABILITADO);
        funcionario.setCargo("CLT"); // Não estagiário

        funcionariosList = Arrays.asList(funcionario);

        folhaPagamento = new FolhaPagamento();
        folhaPagamento.setId(1L);
        folhaPagamento.setStatus(StatusFolhaPagamento.PENDENTE);

        logFolhaPagamento = new LogFolhaPagamento();
        logFolhaPagamento.setId(1L);
    }

    @Test
    void deveGerarFolhaPagamentoComSucesso() {
        // Arrange
        when(funcionarioServiceImpl.findByStatus(Funcionario.HABILITADO)).thenReturn(funcionariosList);
        when(folhaPagamentoRepository.findByIdFuncionarioIdAndData("1", dataInicio)).thenReturn(folhaPagamento);
        when(logFolhaPagamentoServiceImpl.gerarLogGeradaAtualizada("user-id", dataInicio)).thenReturn(logFolhaPagamento);
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.valueOf(100));
        when(funcionarioServiceImpl.getFGTS(funcionario)).thenReturn(BigDecimal.valueOf(80));
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.valueOf(200));
        when(horaExtraServiceImpl.totalHorasNoMes("1", dataInicio)).thenReturn(BigDecimal.valueOf(10));
        when(funcionarioServiceImpl.calcularValorHoraExtra(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(horaExtraServiceImpl.findByFuncionarioAndMesAno("1", dataInicio)).thenReturn(List.of(new HoraExtra()));
        // Return the argument saved so fields set in service are preserved
        when(folhaPagamentoRepository.save(any(FolhaPagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(logSubFolhaPagamentoServiceImpl.gerarLogAtualizado(eq(1L), any(FolhaPagamento.class))).thenReturn(new LogSubFolhaPagamento());

        // Act
        service.gerarFolhaPagamento(token);

        // Assert
        verify(funcionarioServiceImpl, times(1)).findByStatus(Funcionario.HABILITADO);
        verify(logFolhaPagamentoServiceImpl, times(1)).gerarLogGeradaAtualizada("user-id", dataInicio);
        verify(folhaPagamentoRepository, times(1)).findByIdFuncionarioIdAndData("1", dataInicio);
        verify(logSubFolhaPagamentoServiceImpl, times(1)).gerarLogAtualizado(eq(1L), any(FolhaPagamento.class));
        verify(folhaPagamentoRepository, times(1)).save(any(FolhaPagamento.class));
    }

    @Test
    void deveLancarRuntimeExceptionAoGerarFolhaPagamentoFalha() {
        // Arrange
        when(funcionarioServiceImpl.findByStatus(Funcionario.HABILITADO)).thenThrow(new RuntimeException("Erro ao buscar funcionários"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.gerarFolhaPagamento(token);
        });
        assertEquals("Erro ao buscar funcionários", exception.getMessage());
    }

    @Test
    void deveGerarPorFuncionarioComSucesso() {
        // Arrange
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.valueOf(100));
        when(funcionarioServiceImpl.getFGTS(funcionario)).thenReturn(BigDecimal.valueOf(80));
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.valueOf(200));
        when(horaExtraServiceImpl.totalHorasNoMes("1", dataInicio)).thenReturn(BigDecimal.valueOf(10));
        when(funcionarioServiceImpl.calcularValorHoraExtra(funcionario)).thenReturn(BigDecimal.valueOf(50));
        when(horaExtraServiceImpl.findByFuncionarioAndMesAno("1", dataInicio)).thenReturn(List.of(new HoraExtra()));
        when(folhaPagamentoRepository.save(any(FolhaPagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        FolhaPagamento result = service.gerarPorFuncionario(funcionario, dataInicio, new FolhaPagamento());

        // Assert
        assertNotNull(result);
        assertEquals(StatusFolhaPagamento.PENDENTE, result.getStatus());
        assertEquals(dataInicio, result.getData());
        assertEquals(BigDecimal.valueOf(100), result.getINSS());
        assertEquals(BigDecimal.valueOf(80), result.getFGTS());
        assertEquals(BigDecimal.valueOf(50), result.getIRRF());
        assertEquals(BigDecimal.valueOf(230), result.getTotalValorImposto()); // 100+80+50
        assertEquals(BigDecimal.valueOf(200), result.getTotalValorBeneficios());
        assertEquals(BigDecimal.valueOf(10), result.getTotalHorasExtras());
        assertEquals(BigDecimal.valueOf(500), result.getTotalValorHorasExtras()); // 10*50
        assertEquals(BigDecimal.valueOf(1000), result.getSalarioBruto());
        verify(folhaPagamentoRepository, times(1)).save(any(FolhaPagamento.class));
    }

    @Test
    void deveLancarRuntimeExceptionAoGerarPorFuncionarioFalha() {
        // Arrange
        when(funcionarioServiceImpl.getINSS(funcionario)).thenThrow(new RuntimeException("Erro no INSS"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.gerarPorFuncionario(funcionario, dataInicio, new FolhaPagamento());
        });
        assertEquals("Erro no INSS", exception.getMessage());
    }

    @Test
    void deveGerarPorFuncionarioParaEstagiarioSemHorasExtras() {
        // Arrange
        funcionario.setCargo("ESTAGIARIO");
        when(funcionarioServiceImpl.getINSS(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getFGTS(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getIRRF(funcionario)).thenReturn(BigDecimal.ZERO);
        when(funcionarioServiceImpl.getTotalValorBeneficios(funcionario)).thenReturn(BigDecimal.ZERO);
        when(folhaPagamentoRepository.save(any(FolhaPagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        FolhaPagamento result = service.gerarPorFuncionario(funcionario, dataInicio, new FolhaPagamento());

        // Assert
        assertEquals(BigDecimal.ZERO, result.getTotalHorasExtras());
        assertEquals(BigDecimal.ZERO, result.getTotalValorHorasExtras());
        verify(horaExtraServiceImpl, never()).totalHorasNoMes(anyString(), any(LocalDate.class));
    }
}