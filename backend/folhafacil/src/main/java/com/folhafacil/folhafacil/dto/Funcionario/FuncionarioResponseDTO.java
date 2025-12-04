package com.folhafacil.folhafacil.dto.Funcionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDTO {
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
}
