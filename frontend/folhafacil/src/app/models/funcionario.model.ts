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
}