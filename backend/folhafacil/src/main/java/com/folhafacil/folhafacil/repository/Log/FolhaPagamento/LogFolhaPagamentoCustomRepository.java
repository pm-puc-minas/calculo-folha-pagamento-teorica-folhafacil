package com.folhafacil.folhafacil.repository.Log.FolhaPagamento;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.LogFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.dto.Log.LogFilterDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFolhaPagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogFolhaPagamentoCustomRepository {
    private final EntityManager em;


    public List<LogFolhaPagamentoResponseDTO> buscar(LogFilterDTO f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LogFolhaPagamentoResponseDTO> query = cb.createQuery(LogFolhaPagamentoResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<LogFolhaPagamento> logRoot = query.from(LogFolhaPagamento.class);

        Join<LogFolhaPagamento, Funcionario> responsavelJoin = cb.treat(
                logRoot.join("idResponsavel", JoinType.INNER), Funcionario.class
        );

        query.select(cb.construct(
                LogFolhaPagamentoResponseDTO.class,
                logRoot.get("id"),
                logRoot.get("idResponsavel").get("id"),
                responsavelJoin.get("usuario"),
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

        TypedQuery<LogFolhaPagamentoResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
