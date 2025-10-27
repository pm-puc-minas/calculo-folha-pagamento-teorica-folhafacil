CREATE TABLE LogFuncionario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idResponsavel NVARCHAR(50) NOT NULL,
    idManipulado NVARCHAR(50) NOT NULL,
    data DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    mensagem VARCHAR(150) NOT NULL,
    tipo VARCHAR(15) NOT NULL CHECK (tipo IN ('CRIADO', 'ALTERADO', 'DESABILITADO', 'HABILITADO')),
    CONSTRAINT FK_LogFuncionario_Responsavel FOREIGN KEY (idResponsavel)
    REFERENCES Funcionario(id),
    CONSTRAINT FK_LogFuncionario_Manipulado FOREIGN KEY (idManipulado)
    REFERENCES Funcionario(id)
);
