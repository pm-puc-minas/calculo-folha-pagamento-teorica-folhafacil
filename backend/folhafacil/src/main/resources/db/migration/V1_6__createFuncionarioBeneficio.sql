CREATE TABLE FuncionarioBeneficio (
    idFuncionario NVARCHAR(50) NOT NULL,
    idBeneficio INT NOT NULL,
    valor DECIMAL(9,2),
    CONSTRAINT fk_Funcionario_Beneficio FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id),
    CONSTRAINT fk_Beneficio_Funcionario FOREIGN KEY (idBeneficio) REFERENCES Beneficio(id)
);
