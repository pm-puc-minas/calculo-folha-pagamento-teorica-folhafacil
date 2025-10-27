package com.folhafacil.folhafacil.repository.Log.Funcionario;

import com.folhafacil.folhafacil.dto.Log.Funcionario.LogFuncionarioResponseDTO;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.LogFuncionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogFuncionarioCustomRepository {
    private final EntityManager em;

    public List<LogFuncionarioResponseDTO> buscar() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LogFuncionarioResponseDTO> query = cb.createQuery(LogFuncionarioResponseDTO.class);

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

        TypedQuery<LogFuncionarioResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
