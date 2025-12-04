package com.folhafacil.folhafacil.dto.Funcionario;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class FuncionarioFilterDTO {
    private String nome;
    private String cpf;
    private String email;
    private String cargo;
    private Boolean status;
    private List<Long> beneficios;
}
