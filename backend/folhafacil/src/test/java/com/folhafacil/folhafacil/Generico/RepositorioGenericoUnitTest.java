package com.folhafacil.folhafacil.Generico;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.repository.Beneficio.BeneficioCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepositorioGenericoUnitTest {

    private EntityManager em;
    private BeneficioCustomRepository repository;

    @BeforeEach
    void setUp() {
        em = Mockito.mock(EntityManager.class);
        repository = new BeneficioCustomRepository(em);
    }

    // sera realizado o teste com o buscar do BeneficioCustomRepository
    @Test
    void deveExecutarBuscarComSucesso() {
         // Arrange
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<BeneficioResponseDTO> cq = mock(CriteriaQuery.class);
        Root<Beneficio> beneficioRoot = mock(Root.class);
        Subquery<Long> sub = mock(Subquery.class);
        Root<FuncionarioBeneficio> fb = mock(Root.class);
        TypedQuery<BeneficioResponseDTO> query = mock(TypedQuery.class);

        // Configuração dos comportamentos dos mocks
        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(BeneficioResponseDTO.class)).thenReturn(cq);
        when(cq.from(Beneficio.class)).thenReturn(beneficioRoot);
        when(cq.subquery(Long.class)).thenReturn(sub);

        when(sub.from(FuncionarioBeneficio.class)).thenReturn(fb);

        when(em.createQuery(cq)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(
                new BeneficioResponseDTO(1L, "Vale Alimentação", 5L)
        ));

        // Act
        List<BeneficioResponseDTO> result = repository.buscar();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Vale Alimentação", result.get(0).getNome());

        verify(em, times(1)).getCriteriaBuilder();
        verify(em, times(1)).createQuery(cq);
    }
}
