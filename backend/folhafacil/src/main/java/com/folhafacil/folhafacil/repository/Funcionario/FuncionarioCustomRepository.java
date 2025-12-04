package com.folhafacil.folhafacil.repository.Funcionario;

import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioFilterDTO;
import com.folhafacil.folhafacil.dto.Funcionario.FuncionarioResponseDTO;
import com.folhafacil.folhafacil.dto.FuncionarioBeneficio.FuncionarioBeneficioDTO;
import com.folhafacil.folhafacil.entity.Beneficio;
import com.folhafacil.folhafacil.entity.FolhaPagamento;
import com.folhafacil.folhafacil.entity.Funcionario;
import com.folhafacil.folhafacil.entity.FuncionarioBeneficio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FuncionarioCustomRepository {
    private final EntityManager em;

    public List<FuncionarioResponseDTO> buscar(FuncionarioFilterDTO f){

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FuncionarioResponseDTO> query = cb.createQuery(FuncionarioResponseDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<Funcionario> root = query.from(Funcionario.class);

        query.select(cb.construct(
                FuncionarioResponseDTO.class,
                root.get("id"),
                root.get("nome"),
                root.get("cpf"),
                root.get("telefone"),
                root.get("endereco"),
                root.get("email"),
                root.get("dataNascimento"),
                root.get("cargo"),
                root.get("dataAdmissao"),
                root.get("salarioBase"),
                root.get("horasDiarias"),
                root.get("diasMensal"),
                root.get("numDependentes"),
                root.get("pensao"),
                root.get("usuario"),
                root.get("status")
        ));

        if(f.getNome() != null){
            predicates.add(cb.like(root.get("nome"), "%" + f.getNome() + "%"));
        }

        if(f.getCpf() != null){
            predicates.add(cb.like(root.get("cpf"), "%" + f.getCpf() + "%"));
        }

        if(f.getEmail() != null){
            predicates.add(cb.like(root.get("email"), "%" + f.getEmail() + "%"));
        }

        if(f.getCargo() != null){
            predicates.add(cb.equal(root.get("cargo"), f.getCargo()));
        }

        if(f.getStatus() !=null){
            predicates.add(cb.equal(root.get("status"), f.getStatus()));
        }

        if(!f.getBeneficios().isEmpty()){
            predicates.add(root.get("beneficios").get("beneficio").get("id").in(f.getBeneficios()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FuncionarioResponseDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<FuncionarioBeneficioDTO> buscarBeneficios(String uid){CriteriaBuilder cb = em.getCriteriaBuilder();
        if(uid == null){
            return null;
        }
        CriteriaQuery<FuncionarioBeneficioDTO> query = cb.createQuery(FuncionarioBeneficioDTO.class);

        List<Predicate> predicates = new ArrayList<>();

        Root<FuncionarioBeneficio> root = query.from(FuncionarioBeneficio.class);

        Join<FuncionarioBeneficio, Beneficio> beneficioJoin = cb.treat(
                root.join("beneficio", JoinType.INNER), Beneficio.class
        );

        query.select(cb.construct(
                FuncionarioBeneficioDTO.class,
                root.get("beneficio").get("id"),
                beneficioJoin.get("nome"),
                root.get("valor")
        ));

        predicates.add(cb.equal(root.get("funcionario").get("id"), uid));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<FuncionarioBeneficioDTO> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}
