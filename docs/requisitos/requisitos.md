# 📄 Documento de Requisitos – Sistema de Folha de Pagamento

## 1. Introdução

A Folha de Pagamento é um documento mensal que apresenta um registro detalhado das remunerações, encargos e benefícios concedidos aos funcionários de uma empresa. Além de ser uma obrigação legal, ela é uma importante ferramenta de gestão, pois permite às empresas controlarem seus custos com pessoal e acompanhar a evolução das remunerações e encargos sociais.  

Além disso, é por meio dela que os funcionários podem conferir se o valor pago está correto e se seus direitos trabalhistas estão sendo respeitados.  

A administração e composição da Folha de Pagamento é um dos processos mensais mais trabalhosos de um Departamento Pessoal. É nessa rotina que o profissional de Gestão de Pessoas realiza o controle e os cálculos de todos os pagamentos dos salários dos colaboradores de uma empresa, incluindo:

- **Adicionais**: horas extras, adicional noturno, comissões;  
- **Benefícios**: vale transporte, vale alimentação, salário família;  
- **Descontos**: imposto de renda, contribuição previdenciária, FGTS, faltas e atrasos.  

Ela deve ser emitida mensalmente e arquivada pela empresa por um período de, no mínimo, cinco anos. Além disso, é importante que a empresa esteja em conformidade com as leis trabalhistas e previdenciárias, para evitar problemas legais e garantir o bem-estar dos funcionários.  

Entre as informações que geralmente constam em uma folha de pagamento estão o nome e CPF do funcionário, cargo ocupado, salário base, horas trabalhadas, adicionais, descontos, benefícios, entre outros.  

A partir dessas informações, é possível calcular o valor líquido a ser pago ao funcionário, ou seja, o valor que ele irá receber após todos os descontos e benefícios serem considerados.

---

## 📌 Características do Produto

Esta seção define e descreve as características principais do software que será implementado.  
Características são as funcionalidades em alto nível do sistema que são necessárias para fornecer benefícios aos usuários.

- **Cálculo de Jornada de Trabalho**: o sistema deve permitir que o usuário obtenha o valor do salário por hora a partir do salário bruto informado.  
- **Cálculo de Adicionais**: o sistema deve permitir que o usuário calcule os adicionais de periculosidade e insalubridade, caso o funcionário seja apto em receber esses adicionais no salário.  
- **Cálculo de Benefícios**: o sistema deve permitir que o usuário calcule os benefícios de vale transporte e vale alimentação.  
- **Cálculo de Descontos**: o sistema deve permitir que o usuário calcule os descontos de INSS, FGTS e IRRF.  
- **Cálculo do Salário Líquido**: o sistema deve calcular o salário líquido do funcionário, ou seja, seu salário final após todos os benefícios e descontos serem considerados.  
- **Relatório da Folha de Pagamento**: o sistema deve mostrar na tela para o usuário os cálculos da folha de pagamento.  

---

## ✅ Requisitos Funcionais  

### **RF1 - Calcular Salário Hora**  
O sistema deve ser capaz de calcular o valor do salário por hora de um funcionário a partir do seu salário bruto.  

**Exemplo:**  
- Salário Bruto: R$ 2.500,00  
- Jornada Mensal: 200 horas  
- **Salário/Hora = R$ 12,50**  

---

### **RF2 - Calcular Periculosidade**  
- Adicional de 30% sobre o salário bruto.  
- Aplicável a atividades com risco iminente de morte (explosivos, inflamáveis, eletricidade etc.).  

---

### **RF3 - Calcular Insalubridade**  
- Adicional de 10%, 20% ou 40% **sobre o salário mínimo**.  
- Definido conforme laudo técnico (baixo, médio ou alto risco).  

**Exemplo:**  
- Salário Mínimo: R$ 1.380,60  
- Grau Médio (20%)  
- **Adicional = R$ 276,12**  

---

### **RF4 - Calcular Vale Transporte**  
- Desconto de até **6% do salário bruto**.  
- Caso o valor dos vales seja menor, desconta-se apenas o valor concedido.  

---

### **RF5 - Calcular Vale Alimentação**  
- Benefício fornecido pela empresa.  
- Cálculo: **dias úteis x valor diário**.  
- Não compõe o salário.  

**Exemplo:**  
- Valor diário: R$ 24,00  
- 26 dias úteis  
- **Vale Alimentação = R$ 624,00**  

---

### **RF6 - Calcular Desconto de INSS**  
Contribuição progressiva sobre o salário bruto, conforme faixas:  

| Faixa | Salário de Contribuição (R$) | Alíquota (%) |
|-------|-------------------------------|--------------|
| 1ª    | Até 1.302,00                  | 7,5%         |
| 2ª    | 1.302,01 – 2.571,29           | 9%           |
| 3ª    | 2.571,30 – 3.856,94           | 12%          |
| 4ª    | 3.856,95 – 7.507,49           | 14%          |  

- Cálculo **progressivo**.  
- Teto máximo: R$ 877,24 (2023).  

---

### **RF7 - Calcular FGTS**  
- Depósito obrigatório de **8% do salário bruto** na conta vinculada do trabalhador.  

---

### **RF8 - Calcular Desconto de IRRF**  
- Base de cálculo = Salário Bruto – INSS – Dedução por Dependentes (R$ 189,59 cada).  
- Aplicar tabela de incidência mensal:  

| Faixa | Base de Cálculo (R$)        | Alíquota (%) | Dedução (R$) |
|-------|------------------------------|--------------|--------------|
| 1ª    | Até 1.903,98                 | -            | -            |
| 2ª    | 1.903,99 – 2.826,65          | 7,5%         | 142,80       |
| 3ª    | 2.826,66 – 3.751,05          | 15%          | 354,80       |
| 4ª    | 3.751,06 – 4.664,68          | 22,5%        | 636,13       |
| 5ª    | Acima de 4.664,68            | 27,5%        | 869,36       |  

---

### **RF9 - Calcular Salário Líquido**  
- **Salário Líquido = Salário Bruto – (Descontos obrigatórios + Descontos opcionais)**  

---

### **RF10 - Exibir Relatório**  
O sistema deve exibir um relatório completo da folha de pagamento com:  
- Nome do colaborador  
- Data de admissão  
- Mês de referência  
- Cargo  
- Salário bruto e salário/hora  
- Proventos (salário, adicionais, comissões etc.)  
- Descontos (INSS, IRRF, FGTS, VT, VA, faltas, atrasos etc.)  
- Salário líquido  
- Bases de cálculo (INSS, FGTS, IRRF)  

---

## ⚙️ Requisitos Não Funcionais  

- **Usabilidade**  
  - Interface simples e intuitiva.  
  - Mensagens bem formatadas e claras.  

- **Manutenabilidade**  
  - Paradigma: **Orientado a Objetos**.  
  - Baixo acoplamento e alta coesão.  
  - Código limpo, organizado e estruturado.  
  - Seguir convenções de código da linguagem **Java**.  

---

