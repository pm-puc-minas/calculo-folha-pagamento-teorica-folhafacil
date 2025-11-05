CREATE TABLE LogSubFolhaPagamento (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idLogFolhaPagamento INT NOT NULL,
    idFolhaPagamento INT NOT NULL,
    mensagem VARCHAR(150) NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('GERADO', 'ATUALIZADO', 'PAGO', 'ERRO')),
    CONSTRAINT FK_LogSubFolhaPagamento_LogFolhaPagamento FOREIGN KEY (idLogFolhaPagamento) REFERENCES LogFolhaPagamento(id),
    CONSTRAINT FK_LogSubFolhaPagamento_FolhaPagamento FOREIGN KEY (idFolhaPagamento) REFERENCES FolhaPagamento(id)
);
