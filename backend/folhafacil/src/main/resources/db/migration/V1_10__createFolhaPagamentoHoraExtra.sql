CREATE TABLE FolhaPagamentoHoraExtra (
    idFolhaPagamento INT NOT NULL,
    idHoraExtra INT NOT NULL,
    CONSTRAINT fk_FolhaPagamento_HoraExtra FOREIGN KEY (idFolhaPagamento) REFERENCES FolhaPagamento(id),
    CONSTRAINT fk_HoraExtra_FolhaPagamento FOREIGN KEY (idHoraExtra) REFERENCES HoraExtra(id)
);
