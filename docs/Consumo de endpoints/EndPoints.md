# Folha Fácil — Catálogo de Endpoints

Documentação dos principais módulos e serviços REST disponíveis na API Folha Fácil.

---

## 1. Módulo Benefício

### 1.1 Criar ou Atualizar Benefício
- **Endpoints:**  
  - `POST /beneficio`  
  - `POST /beneficio/altera`
- **Descrição:** Salva ou atualiza informações de um benefício.
- **Request Body (`BeneficioDTO`):**
  ```json
  {
    "id": null,
    "nome": "Vale Alimentação"
  }
  ```
- **Response (`BeneficioResponseDTO`):**
  ```json
  {
    "id": 1,
    "nome": "Vale Alimentação",
    "uso": 0
  }
  ```

### 1.2 Listar Benefícios
- **Endpoint:** `GET /beneficio/buscar`
- **Descrição:** Retorna todos os benefícios cadastrados.
- **Response (`List<BeneficioResponseDTO>`):**
  ```json
  [
    {
      "id": 1,
      "nome": "Vale Alimentação",
      "uso": 5
    },
    {
      "id": 2,
      "nome": "Vale Transporte",
      "uso": 11
    }
  ]
  ```

### 1.3 Listar Funcionários por Benefício
- **Endpoint:** `GET /beneficio/{id}/funcionarios`
- **Descrição:** Lista funcionários que utilizam o benefício informado.
- **Response (`List<BeneficioFuncionarioResponseDTO>`):**
  ```json
  [
    {
      "nomeFuncionario": "Pedro Vieira",
      "usuarioFuncionario": "pedro.vieira",
      "valor": 350.00
    }
  ]
  ```

### 1.4 Excluir Benefício
- **Endpoint:** `DELETE /beneficio/{id}/excluir`
- **Descrição:** Remove o benefício indicado.
- **Response:**
  ```json
  {
    "mensagem": "Benefício excluído com sucesso."
  }
  ```

---

## 2. Módulo Funcionário

### 2.1 Criar ou Atualizar Funcionário
- **Endpoints:**  
  - `POST /funcionario`  
  - `POST /funcionario/altera`
- **Descrição:** Cria ou altera dados cadastrais de um funcionário.
- **Request Body (`FuncionarioDTO`):**
  ```json
  {
    "id": "abc123",
    "nome": "Pedro Vieira",
    "cpf": "12345678900",
    "telefone": "31999999999",
    "endereco": "Rua Exemplo 123",
    "email": "pedro@email.com",
    "dataNascimento": "2000-05-20",
    "cargo": "Analista",
    "dataAdmissao": "2023-01-10",
    "salarioBase": 3000.00,
    "horasDiarias": 8,
    "diasMensal": 22,
    "numDependentes": 0,
    "pensao": 0,
    "usuario": "pedro.vieira",
    "status": true,
    "beneficios": [
      {
        "idBeneficio": 1,
        "nomeBeneficio": "Vale Alimentação",
        "valor": 350.00
      }
    ]
  }
  ```
- **Response (`FuncionarioResponseDTO`):**
  ```json
  {
    "id": "abc123",
    "nome": "Pedro Vieira",
    "cpf": "12345678900",
    "telefone": "31999999999",
    "endereco": "Rua Exemplo 123",
    "email": "pedro@email.com",
    "dataNascimento": "2000-05-20",
    "cargo": "Analista",
    "dataAdmissao": "2023-01-10",
    "salarioBase": 3000.00,
    "horasDiarias": 8,
    "diasMensal": 22,
    "numDependentes": 0,
    "pensao": 0,
    "usuario": "pedro.vieira",
    "status": true
  }
  ```

### 2.2 Listar Funcionários
- **Endpoint:** `GET /funcionario/buscar`
- **Descrição:** Retorna funcionários filtrados por critérios opcionais.
- **Request Body Opcional (`FuncionarioFilterDTO`):**
  ```json
  {
    "nome": "Pedro",
    "cpf": null,
    "email": null,
    "cargo": null,
    "status": true,
    "beneficios": [1]
  }
  ```
- **Response (`List<FuncionarioResponseDTO>`):**
  ```json
  [
    {
      "id": "abc123",
      "nome": "Pedro Vieira",
      "cpf": "12345678900",
      "telefone": "31999999999",
      "endereco": "Rua Exemplo",
      "email": "pedro@email.com",
      "dataNascimento": "2000-05-20",
      "cargo": "Analista",
      "dataAdmissao": "2023-01-10",
      "salarioBase": 3000.00,
      "horasDiarias": 8,
      "diasMensal": 22,
      "numDependentes": 0,
      "pensao": 0,
      "usuario": "pedro.vieira",
      "status": true
    }
  ]
  ```

### 2.3 Desativar Funcionário
- **Endpoint:** `DELETE /funcionario/{id}/excluir`
- **Descrição:** Desativa o cadastro do funcionário informado.
- **Response:**
  ```json
  {
    "mensagem": "Funcionário desativado com sucesso."
  }
  ```

---

## 3. Módulo Hora Extra

### 3.1 Criar Hora Extra
- **Endpoint:** `POST /hora-extra/{idFuncionario}`
- **Descrição:** Registra uma nova hora extra para o funcionário.
- **Request Body (`HoraExtraDTO`):**
  ```json
  {
    "descricao": "Suporte emergencial"
  }
  ```
- **Response (`HoraExtraResponseDTO`):**
  ```json
  {
    "id": 10,
    "idFuncionario": "abc123",
    "usuario": "pedro.vieira",
    "dataInicio": "2025-12-07T18:00:00",
    "dataFim": null,
    "descricao": "Suporte emergencial",
    "status": "EM_ANDAMENTO"
  }
  ```

### 3.2 Concluir Hora Extra
- **Endpoint:** `POST /hora-extra/{id}/concluir`
- **Descrição:** Finaliza o registro de hora extra informado.
- **Response:**
  ```json
  {
    "id": 10,
    "status": "CONCLUIDA"
  }
  ```

### 3.3 Cancelar Hora Extra
- **Endpoint:** `POST /hora-extra/{id}/cancelar`
- **Descrição:** Cancela a hora extra informada.
- **Response:**
  ```json
  {
    "id": 10,
    "status": "CANCELADA"
  }
  ```

### 3.4 Buscar Horas Extras
- **Endpoint:** `POST /hora-extra/buscar`
- **Descrição:** Retorna horas extras com base nos filtros fornecidos.
- **Request Body (`HoraExtraFilterDTO`):**
  ```json
  {
    "idFuncionario": "abc123",
    "dataInicio": "2025-12-01T00:00:00",
    "dataFim": "2025-12-07T23:59:59"
  }
  ```
- **Response (`List<HoraExtraResponseDTO>`):**
  ```json
  [
    {
      "id": 10,
      "idFuncionario": "abc123",
      "usuario": "pedro.vieira",
      "dataInicio": "2025-12-07T18:00:00",
      "dataFim": "2025-12-07T20:00:00",
      "descricao": "Suporte emergencial",
      "status": "CONCLUIDA"
    }
  ]
  ```

---

## 4. Módulo Folha de Pagamento

### 4.1 Buscar Folhas de Pagamento
- **Endpoint:** `POST /folha/buscar`
- **Descrição:** Consulta folhas de pagamento aplicando filtros.
- **Request Body (`FolhaPagamentoFilterDTO`):**
  ```json
  {
    "ids": [1, 2, 3],
    "data": "2025-12-01",
    "funcionarios": ["abc123"],
    "status": "PENDENTE",
    "tipoFuncionario": "FIXO"
  }
  ```
- **Response (`List<FolhaPagamentoResponseDTO>`):**
  ```json
  [
    {
      "id": 1,
      "idFuncionario": "abc123",
      "usuarioFuncionario": "pedro.vieira",
      "status": "PENDENTE",
      "data": "2025-12-01",
      "INSS": 200.00,
      "FGTS": 240.00,
      "IRRF": 0.00,
      "totalValorImposto": 440.00,
      "totalValorBeneficios": 350.00,
      "totalHorasExtras": 2.0,
      "totalValorHorasExtras": 80.00,
      "salarioBruto": 3000.00,
      "salarioLiquido": 2890.00
    }
  ]
  ```

### 4.2 Listar Benefícios da Folha
- **Endpoint:** `GET /folha/{id}/beneficios`
- **Descrição:** Exibe os benefícios associados a uma folha.
- **Response (`List<FolhaPagamentoBeneficioResponseDTO>`):**
  ```json
  [
    {
      "nomeBeneficio": "Vale Alimentação",
      "valorBeneficio": 350.00
    }
  ]
  ```

### 4.3 Listar Horas Extras da Folha
- **Endpoint:** `GET /folha/{id}/horas-extras`
- **Descrição:** Exibe horas extras registradas em uma folha.
- **Response (`List<FolhaPagamentoHoraExtraResponseDTO>`):**
  ```json
  [
    {
      "dataInicio": "2025-12-07T18:00:00",
      "dataFim": "2025-12-07T20:00:00",
      "valorHora": 40.00
    }
  ]
  ```

---

## 5. Módulo Logs

### 5.1 Logs de Funcionários
- **Endpoint:** `POST /logs/funcionario`
- **Descrição:** Retorna registros de alteração de funcionários em um intervalo.
- **Request Body (`LogFilterDTO`):**
  ```json
  {
    "dataInicio": "2025-12-01T00:00:00",
    "dataFim": "2025-12-07T23:59:59"
  }
  ```
- **Response (`List<LogFuncionarioResponseDTO>`):**
  ```json
  [
    {
      "id": 50,
      "idResponsavel": "admin",
      "nomeResponsavel": "Administrador",
      "idManipulado": "abc123",
      "nomeManipulado": "Pedro Vieira",
      "data": "2025-12-05T14:03:00",
      "mensagem": "Funcionário alterado",
      "tipo": "ALTERADO"
    }
  ]
  ```

### 5.2 Logs de Subfolha de Pagamento
- **Endpoint:** `POST /logs/subfolha`
- **Descrição:** Lista atualizações realizadas em subfolhas de pagamento.
- **Response (`List<LogSubFolhaPagamentoResponseDTO>`):**
  ```json
  [
    {
      "id": 100,
      "usuarioFuncionario": "pedro.vieira",
      "mensagem": "SubFolha atualizada",
      "totalValorImposto": 440.00,
      "totalValorBeneficios": 350.00,
      "totalHorasExtras": 2,
      "totalValorHorasExtras": 80.00,
      "salarioBruto": 3000.00,
      "tipo": "ATUALIZADO"
    }
  ]
  ```

### 5.3 Logs de Folha de Pagamento
- **Endpoint:** `POST /logs/folha`
- **Descrição:** Lista eventos relacionados à geração ou atualização de folhas de pagamento.
- **Response (`List<LogFolhaPagamentoResponseDTO>`):**
  ```json
  [
    {
      "id": 70,
      "idResponsavel": "admin",
      "usuarioResponsavel": "Administrador",
      "data": "2025-12-02T10:20:00",
      "mensagem": "Folha gerada",
      "tipo": "GERADA_ATUALIZADA"
    }
  ]
  ```