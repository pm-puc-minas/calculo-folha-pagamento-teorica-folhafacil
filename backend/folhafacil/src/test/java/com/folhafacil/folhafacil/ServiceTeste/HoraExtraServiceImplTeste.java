package com.folhafacil.folhafacil.ServiceTeste;

import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraFilterDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraReponseDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.StatusHoraExtra;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.HoraExtra;
import com.folhafacil.folhafacil.repository.HoraExtra.HoraExtraCustomRepository;
import com.folhafacil.folhafacil.repository.HoraExtra.HoraExtraRepository;
import com.folhafacil.folhafacil.service.KeycloakService;
import com.folhafacil.folhafacil.service.HoraExtra.HoraExtraServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HoraExtraServiceImplUnitTest {

    @Mock
    private HoraExtraRepository horaExtraRepository;

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private HoraExtraCustomRepository horaExtraCustomRepository;

    @InjectMocks
    private HoraExtraServiceImpl service;

    private Jwt token;
    private HoraExtraDTO horaExtraDTO;
    private HoraExtraFilterDTO filterDTO;
    private List<HoraExtraReponseDTO> responseDTOs;
    private List<HoraExtra> horaExtrasList;
    private Funcionario funcionario;
    private HoraExtra horaExtraEntity;

    @BeforeEach
    void setUp() {
        token = Mockito.mock(Jwt.class);
        when(keycloakService.recuperarUID(token)).thenReturn("user-id");

        horaExtraDTO = new HoraExtraDTO();
        horaExtraDTO.setDescricao("Descrição teste");

        filterDTO = new HoraExtraFilterDTO();
        filterDTO.setIdFuncionario("user-id");

        funcionario = new Funcionario();
        funcionario.setId("user-id");

        horaExtraEntity = new HoraExtra();
        horaExtraEntity.setId(1L);
        horaExtraEntity.setStatus(StatusHoraExtra.EM_ANDAMENTO);
        horaExtraEntity.setDescricao("Descrição teste");

        horaExtrasList = Arrays.asList(
                new HoraExtra() {{
                    setId(1L);
                    setDescricao("Hora 1");
                    setDataInicio(LocalDateTime.of(2025, 11, 1, 9, 0));
                    setDataFim(LocalDateTime.of(2025, 11, 1, 17, 0));
                    setStatus(StatusHoraExtra.CONCLUIDA);
                }},
                new HoraExtra() {{
                    setId(2L);
                    setDescricao("Hora 2");
                    setDataInicio(LocalDateTime.of(2025, 11, 2, 9, 0));
                    setDataFim(LocalDateTime.of(2025, 11, 2, 12, 0));
                    setStatus(StatusHoraExtra.CONCLUIDA);
                }}
        );

        responseDTOs = Arrays.asList(
                new HoraExtraReponseDTO() {{
                    setId(1L);
                    setIdFuncionario("user-id");
                    setUsuario("user");
                    setDataInicio(LocalDateTime.of(2025, 11, 1, 9, 0));
                    setDataFim(LocalDateTime.of(2025, 11, 1, 17, 0));
                    setDescricao("Hora 1");
                    setStatus(StatusHoraExtra.CONCLUIDA);
                }},
                new HoraExtraReponseDTO() {{
                    setId(2L);
                    setIdFuncionario("user-id");
                    setUsuario("user");
                    setDataInicio(LocalDateTime.of(2025, 11, 2, 9, 0));
                    setDataFim(LocalDateTime.of(2025, 11, 2, 12, 0));
                    setDescricao("Hora 2");
                    setStatus(StatusHoraExtra.CONCLUIDA);
                }}
        );
    }

    @Test
    void deveIniciarComSucesso() {
        // Arrange
        when(horaExtraRepository.save(any(HoraExtra.class))).thenReturn(horaExtraEntity);

        // Act
        service.iniciar(horaExtraDTO, token);

        // Assert
        verify(keycloakService, times(1)).recuperarUID(token);
        verify(horaExtraRepository, times(1)).save(any(HoraExtra.class));
    }

    @Test
    void deveLancarRuntimeExceptionAoIniciarFalha() {
        // Arrange
        when(horaExtraRepository.save(any(HoraExtra.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.iniciar(horaExtraDTO, token);
        });
        assertEquals("Erro ao salvar", exception.getMessage());
        verify(horaExtraRepository, times(1)).save(any(HoraExtra.class));
    }

    @Test
    void deveFinalizarComSucesso() {
        // Arrange
        when(horaExtraRepository.findById(eq(1L))).thenReturn(Optional.of(horaExtraEntity));
        when(horaExtraRepository.save(any(HoraExtra.class))).thenReturn(horaExtraEntity);

        // Act
        service.finalizar(1L);

        // Assert
        assertEquals(StatusHoraExtra.CONCLUIDA, horaExtraEntity.getStatus());
        verify(horaExtraRepository, times(1)).findById(eq(1L));
        verify(horaExtraRepository, times(1)).save(horaExtraEntity);
    }

    @Test
    void deveLancarRuntimeExceptionAoFinalizarFalha() {
        // Arrange
        when(horaExtraRepository.findById(eq(1L))).thenThrow(new RuntimeException("Erro ao encontrar"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.finalizar(1L);
        });
        assertEquals("Erro ao encontrar", exception.getMessage());
        verify(horaExtraRepository, times(1)).findById(eq(1L));
    }

    @Test
    void deveCancelarComSucesso() {
        // Arrange
        when(horaExtraRepository.findById(eq(1L))).thenReturn(Optional.of(horaExtraEntity));
        when(horaExtraRepository.save(any(HoraExtra.class))).thenReturn(horaExtraEntity);

        // Act
        service.cancelar(1L);

        // Assert
        assertEquals(StatusHoraExtra.CANCELADA, horaExtraEntity.getStatus());
        verify(horaExtraRepository, times(1)).findById(eq(1L));
        verify(horaExtraRepository, times(1)).save(horaExtraEntity);
    }

    @Test
    void deveLancarRuntimeExceptionAoCancelarFalha() {
        // Arrange
        when(horaExtraRepository.findById(eq(1L))).thenThrow(new RuntimeException("Erro ao encontrar"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.cancelar(1L);
        });
        assertEquals("Erro ao encontrar", exception.getMessage());
        verify(horaExtraRepository, times(1)).findById(eq(1L));
    }

    @Test
    void deveListarMinhasHorasComSucesso() {
        // Arrange
        when(keycloakService.recuperarUID(token)).thenReturn("user-id");
        when(horaExtraCustomRepository.buscar(any(HoraExtraFilterDTO.class))).thenReturn(responseDTOs);

        // Act
        List<HoraExtraReponseDTO> result = service.minhasHoras(token);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user-id", result.get(0).getIdFuncionario());
        verify(keycloakService, times(1)).recuperarUID(token);
        verify(horaExtraCustomRepository, times(1)).buscar(any(HoraExtraFilterDTO.class));
    }

    @Test
    void deveCalcularTotalHorasNoMesComSucesso() {
        // Arrange
        LocalDate data = LocalDate.of(2025, 11, 1);
        when(service.findByFuncionarioAndMesAno(eq("user-id"), eq(data))).thenReturn(horaExtrasList);

        // Act
        BigDecimal result = service.totalHorasNoMes("user-id", data);

        // Assert
        // Hora 1: 8h, Hora 2: 3h = 11h
        assertEquals(0, BigDecimal.valueOf(11).compareTo(result));
    }

    @Test
    void deveCalcularTotalHorasNoMesZeroSemHorasConcluidas() {
        // Arrange
        LocalDate data = LocalDate.of(2025, 11, 1);
        List<HoraExtra> emptyList = Arrays.asList(
                new HoraExtra() {{ setDataFim(null); }}
        );
        when(service.findByFuncionarioAndMesAno(eq("user-id"), eq(data))).thenReturn(emptyList);

        // Act
        BigDecimal result = service.totalHorasNoMes("user-id", data);

        // Assert
        assertEquals(0, BigDecimal.ZERO.compareTo(result));
    }

    @Test
    void deveListarHorasPorFuncionarioAndMesAnoComSucesso() {
        // Arrange
        LocalDate data = LocalDate.of(2025, 11, 1);
        when(horaExtraRepository.findByFuncionarioAndMesAno(eq("user-id"), eq(11), eq(2025))).thenReturn(horaExtrasList);

        // Act
        List<HoraExtra> result = service.findByFuncionarioAndMesAno("user-id", data);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(horaExtraRepository, times(1)).findByFuncionarioAndMesAno(eq("user-id"), eq(11), eq(2025));
    }
}