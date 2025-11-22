package com.folhafacil.folhafacil.repository.Log.FolhaPagamento.Sub;

import com.folhafacil.folhafacil.dto.Log.FolhaPagamento.Sub.LogSubFolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogSubFolhaPagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogSubFolhaPagamentoCustomRepository {
    private final EntityManager em;

    public List<LogSubFolhaPagamentoResponseDTO> buscar(Long idLogFolhaPagamento) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LogSubFolhaPagamentoResponseDTO> query = cb.createQuery(LogSubFolhaPagamentoResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<LogSubFolhaPagamento> logRoot = query.from(LogSubFolhaPagamento.class);

        Join<LogSubFolhaPagamento, FolhaPagamento> folhaJoin = cb.treat(
                logRoot.join("idFolhaPagamento", JoinType.INNER), FolhaPagamento.class
        );

        Join<FolhaPagamento, Funcionario> funcionarioJoin = cb.treat(
                folhaJoin.join("idFuncionario", JoinType.INNER), Funcionario.class
        );

        Expression<String> usuarioFuncionario = funcionarioJoin.get("usuario");

        query.select(cb.construct(
                LogSubFolhaPagamentoResponseDTO.class,
                logRoot.get("id"),
                usuarioFuncionario,
                logRoot.get("mensagem"),
                logRoot.get("totalValorImposto"),
                logRoot.get("totalValorBeneficios"),
                logRoot.get("totalHorasExtras"),
                logRoot.get("totalValorHorasExtras"),
                logRoot.get("salarioBruto"),
                logRoot.get("tipo")
        ));

        if (idLogFolhaPagamento != null) {
            predicates.add(cb.equal(logRoot.get("idLogFolhaPagamento").get("id"), idLogFolhaPagamento));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<LogSubFolhaPagamentoResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
