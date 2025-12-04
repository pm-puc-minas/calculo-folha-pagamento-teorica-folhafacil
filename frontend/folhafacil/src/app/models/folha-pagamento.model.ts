export type FolhaPagamentoResponseDTO = {
    id: number;
    idFuncionario: string;
    usuarioFuncionario: string;
    status: string;
    data: Date;
    INSS: number;
    FGTS: number;
    IRRF: number;
    totalValorImposto: number;
    totalValorBeneficios: number;
    totalHorasExtras: number;
    totalValorHorasExtras: number;
    salarioBruto: number;
    salarioLiquido: number;
}

export type FolhaPagamentoBeneficioResponseDTO = {
    nomeBeneficio: string;
    valorBeneficio: number;
}

export type FolhaPagamentoHoraExtraResponseDTO = {
    dataInicio: Date;
    dataFim: Date;
    valorHora: number;
}

export type FolhaPagamentoFilterDTO = {
    ids: number[];
    data: Date;
    funcionarios: string[];
    status: string;
    tipoFuncionario: string;
}