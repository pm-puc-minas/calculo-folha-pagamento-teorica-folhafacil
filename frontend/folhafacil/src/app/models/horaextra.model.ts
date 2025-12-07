export type HoraExtraDTO = {
    mensagem: string
}

export type HoraExtraResponseDTO = {
    id: number
    idFuncionario : String
    usuario : String
    nomeFuncionario: String
    dataInicio : Date
    dataFim: Date
    descricao : String
    status : String
}

export type HoraExtraFilterDTO = {
    idFuncionario: string;
    dataInicio: Date;
    dataFim: Date;
}