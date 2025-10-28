package com.folhafacil.folhafacil.repository.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogFuncionarioCustomRepository {
    private final EntityManager em;

    public List<LogFuncionarioResponseDTO> buscar(LogFilterDTO f) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LogFuncionarioResponseDTO> query = cb.createQuery(LogFuncionarioResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<LogFuncionario> logRoot = query.from(LogFuncionario.class);

        Join<LogFuncionario, Funcionario> responsavelJoin = cb.treat(
                logRoot.join("idResponsavel", JoinType.INNER), Funcionario.class
        );

        Join<LogFuncionario, Funcionario> manipuladoJoin = cb.treat(
                logRoot.join("idManipulado", JoinType.INNER), Funcionario.class
        );

        query.select(cb.construct(
                LogFuncionarioResponseDTO.class,
                logRoot.get("id"),
                logRoot.get("idResponsavel").get("id"),
                responsavelJoin.get("usuario"),
                logRoot.get("idManipulado").get("id"),
                manipuladoJoin.get("usuario"),
                logRoot.get("data"),
                logRoot.get("mensagem"),
                logRoot.get("tipo")
        ));

        if (f.getDataInicio() != null && f.getDataFim() == null) {
            predicates.add(cb.greaterThanOrEqualTo(logRoot.get("data"), f.getDataInicio()));
        }

        if (f.getDataFim() != null && f.getDataInicio() == null) {
            predicates.add(cb.lessThanOrEqualTo(logRoot.get("data"), f.getDataFim()));
        }

        if (f.getDataInicio() != null && f.getDataFim() != null) {
            predicates.add(cb.between(logRoot.get("data"), f.getDataInicio(), f.getDataFim()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<LogFuncionarioResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
