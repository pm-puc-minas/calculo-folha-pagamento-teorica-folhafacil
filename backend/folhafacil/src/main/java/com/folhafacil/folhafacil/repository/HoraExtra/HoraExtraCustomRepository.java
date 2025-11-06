package com.folhafacil.folhafacil.repository.HoraExtra;

import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraFilterDTO;
import com.folhafacil.folhafacil.dto.HoraExtra.HoraExtraReponseDTO;
import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.HoraExtra;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import com.folhafacil.folhafacil.repository.RepositorioGenerico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HoraExtraCustomRepository extends RepositorioGenerico<HoraExtra> {
    private final EntityManager em;

    public HoraExtraCustomRepository(EntityManager em) {
        super(HoraExtra.class);
        this.em = em;
    }

    public List<HoraExtraReponseDTO> buscar(HoraExtraFilterDTO f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HoraExtraReponseDTO> query = cb.createQuery(HoraExtraReponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<HoraExtra> root = query.from(HoraExtra.class);

        Join<HoraExtra, Funcionario> funcionarioJoin = cb.treat(
                root.join("idFuncionario", JoinType.INNER), Funcionario.class);
        query.select(cb.construct(
                HoraExtraReponseDTO.class,
                root.get("id"),
                root.get("idFuncionario").get("id"),
                funcionarioJoin.get("usuario"),
                root.get("dataInicio"),
                root.get("dataFim"),
                root.get("descricao"),
                root.get("status")));

        if (f.getIdFuncionario() != null) {
            predicates.add(cb.equal(root.get("idFuncionario").get("id"), f.getIdFuncionario()));
        }

        if (f.getDataInicio() != null && f.getDataFim() == null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dataInicio"), f.getDataInicio()));
        }

        if (f.getDataFim() != null && f.getDataInicio() == null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dataInicio"), f.getDataFim()));
        }

        if (f.getDataInicio() != null && f.getDataFim() != null) {
            predicates.add(cb.between(root.get("dataInicio"), f.getDataInicio(), f.getDataFim()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<HoraExtraReponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
