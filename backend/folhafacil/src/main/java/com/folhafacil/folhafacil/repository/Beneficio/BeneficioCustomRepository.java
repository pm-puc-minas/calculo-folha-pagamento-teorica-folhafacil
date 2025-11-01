package com.folhafacil.folhafacil.repository.Beneficio;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BeneficioCustomRepository {
    private final EntityManager em;

    public List<BeneficioResponseDTO> buscar() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BeneficioResponseDTO> cq = cb.createQuery(BeneficioResponseDTO.class);

        Root<Beneficio> beneficioRoot = cq.from(Beneficio.class);

        Subquery<Long> sub = cq.subquery(Long.class);
        Root<FuncionarioBeneficio> fb = sub.from(FuncionarioBeneficio.class);
        sub.select(cb.count(fb));
        sub.where(cb.equal(fb.get("beneficio"), beneficioRoot));

        cq.select(cb.construct(
                BeneficioResponseDTO.class,
                beneficioRoot.get("id"),
                beneficioRoot.get("nome"),
                sub.getSelection()
        ));

        return em.createQuery(cq).getResultList();
    }
}
