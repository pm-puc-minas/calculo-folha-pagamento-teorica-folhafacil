package com.folhafacil.folhafacil.repository.Funcionario;

import com.folhafacil.folhafacil.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
    Funcionario findByCpf(String cpf);
}
