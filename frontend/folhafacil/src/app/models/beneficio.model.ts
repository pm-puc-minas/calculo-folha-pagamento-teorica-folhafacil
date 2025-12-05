export type BeneficioDTO = {
    id: number;
    nome: string;
}

export type BeneficioResponseDTO = {
    id: number;
    nome: string;
    uso: number
}

export type BeneficioFuncionarioResponseDTO = {
    nomeFuncionario: string;
    usuarioFuncionario: string;
    valor: number;
}