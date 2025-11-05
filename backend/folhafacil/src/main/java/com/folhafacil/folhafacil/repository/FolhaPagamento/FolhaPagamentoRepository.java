package com.folhafacil.folhafacil.repository.FolhaPagamento;

import com.folhafacil.folhafacil.entity.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {
    FolhaPagamento findByIdFuncionarioIdAndData(String idFuncionarioId, LocalDate data);
}
