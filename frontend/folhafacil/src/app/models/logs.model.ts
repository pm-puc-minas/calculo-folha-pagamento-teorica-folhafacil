export type LogFilterDTO = {
    dataInicio: Date;
    dataFim: Date;
}

export type LogFuncionarioResponseDTO = { 
    id: number;
    idResponsavel: string;
    nomeResponsavel: string;
    idManipulado: string;
    nomeManipulado: string;
    data: Date;
    mensagem: string;
    tipo: string
}

export type LogFolhaPagamentoResponseDTO = {
    id: number;
    idResponsavel: string;
    usuarioResponsavel: string;
    data: Date;
    mensagem: String;
    tipo: String
}

export type LogSubFolhaPagamentoResponseDTO = {
    id: number;
    usuarioFuncionario: string;
    mensagem: string;
    totalValorImposto: number;
    totalValorBeneficios: number;
    totalHorasExtras: number;
    totalValorHorasExtras: number;
    salarioBruto: number;
    tipo: string;
}