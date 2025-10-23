CREATE TABLE Funcionario (
    id NVARCHAR(50) PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf CHAR(11) NOT NULL,
    telefone CHAR(11),
    endereco VARCHAR(200),
    email VARCHAR(200),
    dataNascimento DATE,
    cargo VARCHAR(10),
    dataAdmissao DATE,
    salarioBase DECIMAL(9,2),
    horasDiarias INT,
    diasMensal INT,
    numDependentes INT,
    pensao DECIMAL(9,2)
);
