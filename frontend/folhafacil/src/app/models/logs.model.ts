export type LogFilterDTO = {
    dataInicio: Date;
    dataFim: Date;
}

export type LogsFuncionarioResponseDTO = { 
    id: number;
    idResponsavel: string;
    nomeResponsavel: string;
    idManipulado: string;
    nomeManipulado: string;
    data: Date;
    mensagem: string;
    tipo: string
}