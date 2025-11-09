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
        if(entidade == null){
            throw new IllegalArgumentException("Entidade nula nao pode ser salva");
        }
        return repository.save(entidade);
    }

    public void deletar(ID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entidade com ID " + id + " não encontrada para exclusão");
        }
        repository.deleteById(id);
    }

    public Optional<T> buscarPorId(ID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entidade com ID " + id + " não encontrada");
        }
        return repository.findById(id);
    }

    public List<T> listarTodos() {
        return repository.findAll();
    }

    public T atualizar(ID id, T entidadeAtualizada) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entidade com ID " + id + " não encontrada para atualização");
        }
        return repository.save(entidadeAtualizada);
    }
}
