CREATE TABLE HoraExtra (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idFuncionario NVARCHAR(50) NOT NULL,
    dataInicio DATETIME2 NOT NULL,
    dataFim DATETIME2,
    descricao VARCHAR(200) NOT NULL,
    status VARCHAR(15) NOT NULL CHECK (status IN ('EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA'))
    CONSTRAINT FK_HoraExtra_Funcionario FOREIGN KEY (idFuncionario) REFERENCES Funcionario(id),
);
