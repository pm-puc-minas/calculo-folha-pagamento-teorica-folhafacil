package com.folhafacil.folhafacil.dto.Funcionario;

import com.folhafacil.folhafacil.dto.FuncionarioBeneficio.FuncionarioBeneficioDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FuncionarioDTO {
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;
    private LocalDate dataNascimento;
    private String cargo;
    private LocalDate dataAdmissao;
    private BigDecimal salarioBase;
    private Integer horasDiarias;
    private Integer diasMensal;
    private Integer numDependentes;
    private BigDecimal pensao;
    private String usuario;
    private Boolean status;
    private List<FuncionarioBeneficioDTO> beneficios;
}
