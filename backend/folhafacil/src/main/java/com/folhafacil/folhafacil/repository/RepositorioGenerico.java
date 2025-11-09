package com.folhafacil.folhafacil.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

@Transactional
public class RepositorioGenerico<T> {

    @PersistenceContext
    private EntityManager em;
    private final Class<T> classe;

    public RepositorioGenerico(Class<T> classe) {
        this.classe = classe;
    }

    public void salvar(T entidade) {
        if(entidade == null){
            throw new IllegalArgumentException("Entidade nula nao pode ser salva");
        }
        em.persist(entidade);
    }

    public T atualizar(T entidade) {
        if(entidade == null){
            throw new IllegalArgumentException("Entidade nula nao pode ser atualizada");
        }   
        return em.merge(entidade);
    }

    public void deletar(Object id) {
        T entidade = em.find(classe, id);
        if (entidade != null) {
            em.remove(entidade);
            throw new IllegalArgumentException("Entidade com ID " + id + " não encontrada para exclusão");
        }
    }

    public T buscarPorId(Object id) {
        if(id == null){
            throw new IllegalArgumentException("ID nulo nao pode ser buscado");
        }
        return em.find(classe, id);
    }

    public List<T> listarTodos() {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(classe);
        cq.select(cq.from(classe));
        return em.createQuery(cq).getResultList();
    }
}
