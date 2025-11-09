package com.folhafacil.folhafacil.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public abstract class ServiceGenerico<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected ServiceGenerico(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T salvar(T entidade) {
        return repository.save(entidade);
    }

    public void deletar(ID id) {
        repository.deleteById(id);
    }

    public Optional<T> buscarPorId(ID id) {
        return repository.findById(id);
    }

    public List<T> listarTodos() {
        return repository.findAll();
    }
    
    public T atualizar(ID id, T entidadeAtualizada) {
        return repository.save(entidadeAtualizada);
    }
}
