package com.folhafacil.folhafacil.repository.FolhaPagamento;

import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoBeneficioResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoFilterDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoHoraExtraResponseDTO;
import com.folhafacil.folhafacil.dto.FolhaPagamento.FolhaPagamentoResponseDTO;
import com.folhafacil.folhafacil.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FolhaPagamentoCustomRepository {
    private final EntityManager em;

    public List<FolhaPagamentoResponseDTO> buscar(FolhaPagamentoFilterDTO f) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FolhaPagamentoResponseDTO> query = cb.createQuery(FolhaPagamentoResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FolhaPagamento> root = query.from(FolhaPagamento.class);

        Join<FolhaPagamento, Funcionario> funcionarioJoin = cb.treat(
                root.join("idFuncionario", JoinType.INNER), Funcionario.class
        );

        query.select(cb.construct(
                FolhaPagamentoResponseDTO.class,
                root.get("id"),
                root.get("idFuncionario").get("id"),
                funcionarioJoin.get("usuario"),
                root.get("status"),
                root.get("data"),
                root.get("INSS"),
                root.get("FGTS"),
                root.get("IRRF"),
                root.get("totalValorImposto"),
                root.get("totalValorBeneficios"),
                root.get("totalHorasExtras"),
                root.get("totalValorHorasExtras"),
                root.get("salarioBruto"),
                root.get("salarioLiquido")
        ));

        if(f.getData() != null) {
            predicates.add(cb.equal(root.get("data"), f.getData()));
        }

        if(!f.getFuncionarios().isEmpty()) {
            predicates.add(root.get("idFuncionario").get("id").in(f.getFuncionarios()));
        }

        if(f.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), f.getStatus()));
        }

        if(f.getTipoFuncionario() != null) {
            predicates.add(cb.equal(funcionarioJoin.get("cargo"), f.getTipoFuncionario()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FolhaPagamentoResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<FolhaPagamentoResponseDTO> buscar(List<Long> ids){
        if(ids.isEmpty()){
            return null;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FolhaPagamentoResponseDTO> query = cb.createQuery(FolhaPagamentoResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FolhaPagamento> root = query.from(FolhaPagamento.class);

        Join<FolhaPagamento, Funcionario> funcionarioJoin = cb.treat(
                root.join("idFuncionario", JoinType.INNER), Funcionario.class
        );

        query.select(cb.construct(
                FolhaPagamentoResponseDTO.class,
                root.get("id"),
                root.get("idFuncionario").get("id"),
                funcionarioJoin.get("usuario"),
                root.get("status"),
                root.get("data"),
                root.get("INSS"),
                root.get("FGTS"),
                root.get("IRRF"),
                root.get("totalValorImposto"),
                root.get("totalValorBeneficios"),
                root.get("totalHorasExtras"),
                root.get("totalValorHorasExtras"),
                root.get("salarioBruto"),
                root.get("salarioLiquido")
        ));

        predicates.add(root.get("id").in(ids));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FolhaPagamentoResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<FolhaPagamentoBeneficioResponseDTO> buscarBeneficios(Long idFolha){
        if(idFolha == null){
            return null;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FolhaPagamentoBeneficioResponseDTO> query = cb.createQuery(FolhaPagamentoBeneficioResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FolhaPagamentoBeneficio> root = query.from(FolhaPagamentoBeneficio.class);

        Join<FolhaPagamentoBeneficio, Beneficio> beneficioJoin = cb.treat(
                root.join("beneficio", JoinType.INNER), Beneficio.class
        );

        query.select(cb.construct(
                FolhaPagamentoBeneficioResponseDTO.class,
                beneficioJoin.get("nome"),
                root.get("valor")
        ));

        predicates.add(cb.equal(root.get("folhaPagamento").get("id"), idFolha));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FolhaPagamentoBeneficioResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<FolhaPagamentoHoraExtraResponseDTO> buscarHorasExtras(Long idFolha){
        if(idFolha == null){
            return null;
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FolhaPagamentoHoraExtraResponseDTO> query = cb.createQuery(FolhaPagamentoHoraExtraResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FolhaPagamentoHoraExtra> root = query.from(FolhaPagamentoHoraExtra.class);

        Join<FolhaPagamentoHoraExtra, HoraExtra> horaExtraJoin = cb.treat(
                root.join("horaExtra", JoinType.INNER), HoraExtra.class
        );

        Join<HoraExtra, Funcionario> funcionarioJoin = cb.treat(
                horaExtraJoin.join("idFuncionario", JoinType.INNER), Funcionario.class
        );

        Expression<Integer> horasMes = cb.prod(
                funcionarioJoin.get("horasDiarias"),
                funcionarioJoin.get("diasMensal")
        );

        Expression<Number> valorHora = cb.quot(
                funcionarioJoin.get("salarioBase"),
                horasMes
        );

        Expression<BigDecimal> valorHoraDecimal = cb.toBigDecimal(valorHora);


        query.select(cb.construct(
                FolhaPagamentoHoraExtraResponseDTO.class,
                horaExtraJoin.get("dataInicio"),
                horaExtraJoin.get("dataFim"),
                valorHoraDecimal
        ));

        predicates.add(cb.equal(root.get("folhaPagamento").get("id"), idFolha));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FolhaPagamentoHoraExtraResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
