/*package com.engsoft.folha_facil.funcionario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.engsoft.folha_facil.model.Funcionario;
import com.engsoft.folha_facil.repository.FuncionarioRepository;
import com.engsoft.folha_facil.service.FuncionarioService;

public class EditarFuncionarioTest {
    @Test
    void testEditFunc() {
    FuncionarioService service = new FuncionarioService();
    FuncionarioRepository repo = new FuncionarioRepository();

    Funcionario fExistente = repo.findById(0L);
    assertNotNull(fExistente, "Funcionário com ID=1 deve existir");
    fExistente.setSalarioBase(5000.00);

    service.alterarFuncionario(fExistente);
    Funcionario fAtualizado = repo.findById(0L);
    assertEquals(5000.00, fAtualizado.getSalarioBase(), 0.01);
    }
}*/
