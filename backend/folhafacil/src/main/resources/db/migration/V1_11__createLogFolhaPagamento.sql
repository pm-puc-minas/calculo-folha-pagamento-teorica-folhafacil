CREATE TABLE LogFolhaPagamento (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idResponsavel NVARCHAR(50) NOT NULL,
    data DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    mensagem VARCHAR(150) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('GERADA_ATUALIZADA', 'PAGAMENTO')),
    CONSTRAINT FK_LogFolhaPagamento_Responsavel FOREIGN KEY (idResponsavel) REFERENCES Funcionario(id),
);
