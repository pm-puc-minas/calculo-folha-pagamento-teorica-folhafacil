package com.folhafacil.folhafacil.repository.Log.FolhaPagamento;

import com.folhafacil.folhafacil.entity.LogFolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogFolhaPagamentoRepository extends JpaRepository<LogFolhaPagamento, Long> {
}
