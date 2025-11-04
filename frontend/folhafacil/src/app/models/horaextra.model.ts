export type HoraExtraDTO = {
    mensagem: string
}

export type HoraExtraResponseDTO = {
    id: number
    idFuncionario : String
    usuario : String
    dataInicio : Date
    dataFim: Date
    descricao : String
    status : String
}