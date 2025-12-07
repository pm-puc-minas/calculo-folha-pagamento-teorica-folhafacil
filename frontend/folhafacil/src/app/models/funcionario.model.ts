export type FuncionarioDTO = {
    id: string;
    nome: string;
    cpf: string;
    telefone: string;
    endereco: string;
    email: string;
    dataNascimento: Date;
    cargo: string;
    dataAdmissao: Date;
    salarioBase: number;
    horasDiarias: number;
    diasMensal: number;
    numDependentes: number;
    pensao: number;
    beneficios: FuncionarioBeneficoDTO[]
}

export type FuncionarioBeneficoDTO = {
    idBeneficio: number;
    nomeBeneficio: string;
    valor: number;
}

export type FuncionarioFilterDTO = {
    nome: string | null;
    cpf: string | null;
    email: string | null;
    cargo: string | null;
    status : boolean | null;
    beneficios: number[];
}

export type FuncionarioResponseDTO = {
    id: string;
    nome: string;
    cpf: string;
    telefone: string;
    endereco: string;
    email: string;
    dataNascimento: Date;
    cargo: string;
    dataAdmissao: Date;
    salarioBase: number;
    horasDiarias: number;
    diasMensal: number;
    numDependentes: number;
    pensao: number;
    status: boolean
}