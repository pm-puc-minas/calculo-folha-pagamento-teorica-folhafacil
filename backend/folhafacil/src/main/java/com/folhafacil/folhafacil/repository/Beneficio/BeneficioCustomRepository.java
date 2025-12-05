package com.folhafacil.folhafacil.repository.Beneficio;

import com.folhafacil.folhafacil.dto.Beneficio.BeneficioFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Beneficio.BeneficioResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import com.folhafacil.folhafacil.repository.RepositorioGenerico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<BeneficioFuncionarioResponseDTO> buscarFuncionarios(Long id){
        if(id == null){
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BeneficioFuncionarioResponseDTO> cq = cb.createQuery(BeneficioFuncionarioResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FuncionarioBeneficio> root = cq.from(FuncionarioBeneficio.class);

        Join<FuncionarioBeneficio, Funcionario> funcionarioJoin = root.join("funcionario");

        cq.select(cb.construct(
                BeneficioFuncionarioResponseDTO.class,
                funcionarioJoin.get("nome"),
                funcionarioJoin.get("usuario"),
                root.get("valor")
        ));

        predicates.add(cb.equal(root.get("beneficio").get("id"), id));

        predicates.add(cb.equal(funcionarioJoin.get("status"), true));
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<BeneficioFuncionarioResponseDTO> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();
    }
}
