CREATE TABLE FolhaPagamentoBeneficio (
    idFolhaPagamento INT NOT NULL,
    idBeneficio INT NOT NULL,
    valor DECIMAL(9,2),
    CONSTRAINT fk_FolhaPagamento_Beneficio FOREIGN KEY (idFolhaPagamento) REFERENCES FolhaPagamento(id),
    CONSTRAINT fk_Beneficio_FolhaPagamento FOREIGN KEY (idBeneficio) REFERENCES Beneficio(id)
);
