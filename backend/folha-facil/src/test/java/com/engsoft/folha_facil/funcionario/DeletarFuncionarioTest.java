package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.FuncionarioService;

public class DeletarFuncionarioTest {
    @Test
        void testDeleteFunc() {
        FuncionarioService service = new FuncionarioService();
        FuncionarioRepository repo = new FuncionarioRepository();

        Funcionario fExistente = repo.findById(0L);
        assertNotNull(fExistente, "Funcionário com ID=0 deve existir antes da exclusão");

        service.excluirFuncionario(fExistente.getId());
        Funcionario fDeletado = repo.findById(0L);
        assertNull(fDeletado, "Funcionário com ID=0 não deve existir após exclusão");
    }
}
