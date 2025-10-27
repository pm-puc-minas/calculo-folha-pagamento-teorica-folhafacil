package com.folhafacil.folhafacil.repository.Log.Funcionario;

import com.folhafacil.folhafacil.entity.LogFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogFuncionarioRepository extends JpaRepository<LogFuncionario, Long> {
}
