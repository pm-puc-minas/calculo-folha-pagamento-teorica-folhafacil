package com.folhafacil.folhafacil.repository.Log.FolhaPagamento.Sub;

import com.folhafacil.folhafacil.entity.LogSubFolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogSubFolhaPagamentoRepository extends JpaRepository<LogSubFolhaPagamento, Long> {
}
