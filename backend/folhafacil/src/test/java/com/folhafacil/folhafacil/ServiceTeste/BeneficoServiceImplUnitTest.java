package com.folhafacil.folhafacil.ServiceTeste;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.mapper.BeneficioMapper;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioCustomRepository;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioRepository;
import com.folhafacil.folhafacil.service.Beneficio.BeneficoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BeneficoServiceImplUnitTest {

    private BeneficioRepository beneficioRepository;
    private BeneficioCustomRepository beneficioCustomRepository;
    private BeneficoServiceImpl service;

    private BeneficioDTO beneficioDTO;
    private Beneficio beneficioEntity;
    private List<Beneficio> beneficiosList;
    private List<BeneficioResponseDTO> responseDTOs;

    @BeforeEach
    void setUp() {
        beneficioRepository = Mockito.mock(BeneficioRepository.class);
        beneficioCustomRepository = Mockito.mock(BeneficioCustomRepository.class);
        service = new BeneficoServiceImpl(beneficioRepository, beneficioCustomRepository);

        beneficioDTO = new BeneficioDTO();
        beneficioDTO.setNome("Benefício Teste");

        beneficioEntity = new Beneficio();
        beneficioEntity.setId(1L);
        beneficioEntity.setNome("Benefício Teste");

        beneficiosList = Arrays.asList(
                new Beneficio() {{ setId(1L); setNome("Benefício A"); }},
                new Beneficio() {{ setId(2L); setNome("Benefício A"); }},
                new Beneficio() {{ setId(3L); setNome("Benefício B"); }}
        );

        responseDTOs = Arrays.asList(
                new BeneficioResponseDTO(1L, "Benefício A", 10L),
                new BeneficioResponseDTO(2L, "Benefício B", 5L)
        );
    }

    @Test
    public void deveSalvarComSucesso() {
       
        Beneficio entityMock = BeneficioMapper.toEntity(beneficioDTO);
        when(beneficioRepository.save(any(Beneficio.class))).thenReturn(entityMock);

       
        service.salvar(beneficioDTO);

        
        verify(beneficioRepository, times(1)).save(any(Beneficio.class));
        verifyNoInteractions(beneficioCustomRepository);
    }

    @Test
    public void deveLancarRuntimeExceptionAoSalvarFalha() {
        
        when(beneficioRepository.save(any(Beneficio.class)))
                .thenThrow(new RuntimeException("Erro ao salvar"));

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.salvar(beneficioDTO);
        });
        assertEquals("Erro ao salvar", exception.getMessage());
        verify(beneficioRepository, times(1)).save(any(Beneficio.class));
    }

    @Test
    public void deveExecutarBuscarComSucesso() {
        
        when(beneficioCustomRepository.buscar()).thenReturn(responseDTOs);

        
        List<BeneficioResponseDTO> result = service.buscar();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Benefício A", result.get(0).getNome());
        verify(beneficioCustomRepository, times(1)).buscar();
        verifyNoInteractions(beneficioRepository);
    }

    @Test
    public void deveDeletarComSucesso() {
        
        doNothing().when(beneficioRepository).deleteById(1L);

        
        service.deletar(1L);

        
        verify(beneficioRepository, times(1)).deleteById(1L);
        verifyNoInteractions(beneficioCustomRepository);
    }

    @Test
    public void deveLancarRuntimeExceptionAoDeletarFalha() {
        
        doThrow(new RuntimeException("Erro ao deletar")).when(beneficioRepository).deleteById(1L);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.deletar(1L);
        });
        assertEquals("Erro ao deletar", exception.getMessage());
        verify(beneficioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deveListarNomesUnicosComSucesso() {
        
        when(beneficioRepository.findAll()).thenReturn(beneficiosList);

        
        Set<String> result = service.listarNomesUnicos();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("Benefício A"));
        assertTrue(result.contains("Benefício B"));
        verify(beneficioRepository, times(1)).findAll();
    }

    @Test
    public void deveAgruparPorNomeComSucesso() {
        
        when(beneficioRepository.findAll()).thenReturn(beneficiosList);

        
        Map<String, List<Beneficio>> result = service.agruparPorNome();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get("Benefício A").size());
        assertEquals(1, result.get("Benefício B").size());
        verify(beneficioRepository, times(1)).findAll();
    }

    @Test
    public void deveFiltrarPorNomeMinimoComSucesso() {
        
        when(beneficioRepository.findAll()).thenReturn(beneficiosList);

        
        List<Beneficio> result = service.filtrarPorNomeMinimo("Benefício A");

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(b -> b.getNome().startsWith("Benefício A")));
        verify(beneficioRepository, times(1)).findAll();
    }

    @Test
    public void deveRetornarListaVaziaAoFiltrarPorNomeMinimoSemMatch() {
        
        when(beneficioRepository.findAll()).thenReturn(beneficiosList);

        
        List<Beneficio> result = service.filtrarPorNomeMinimo("XYZ");

        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(beneficioRepository, times(1)).findAll();
    }
}