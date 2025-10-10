[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=20401669&assignment_repo_type=AssignmentRepo)

```markdown
# Folha-Fácil 

Sistema de apoio para cálculo de folha de pagamento — implementação em Java, com backend modularizado, dados de exemplo e testes automatizados. Este README documenta o que será entregue na 2ª sprint e contém todas as instruções e evidências que a disciplina exige.

Status: pronto para entrega da 2ª sprint (código no diretório backend/folha-facil; wrapper Maven incluído).

1. Sobre o Projeto

Este repositório contém a implementação de um sistema de gestão de folha de pagamento, desenvolvido como parte de um projeto didático da disciplina de Engenharia de Software.

O objetivo do trabalho é implementar um módulo de cálculo de folha de pagamento integrado a um software de gestão de recursos humanos (RH), simulando o funcionamento de um sistema corporativo utilizado por empresas para automatizar o processamento de salários e encargos trabalhistas.

A solução foi desenvolvida em Java, seguindo princípios de Programação Orientada a Objetos (POO) e modularização, de modo a facilitar o entendimento, a manutenção e a realização de testes automatizados.

O projeto foi estruturado para permitir a leitura de dados de funcionários, o cálculo automático de salários brutos e líquidos, e a aplicação de regras de desconto e contribuição (como INSS e IRRF), utilizando arquivos de entrada em formato JSON e testes de validação com JUnit.

2. Arquitetura e Modularização

A aplicação foi desenvolvida seguindo o padrão de arquitetura em camadas, visando
organização, separação de responsabilidades e facilidade de manutenção. Essa estrutura modular facilita futuras expansões, como a integração com bancos de dados ou a criação de uma API REST, e garante que cada camada tenha responsabilidades bem definidas, tornando o código mais legível e escalável.

As principais camadas do sistema são:

- Camada Model:  
  Representa o domínio da aplicação, encapsulando os dados e comportamentos essenciais dos objetos do sistema.  
  Esta camada é responsável por manter a estrutura dos dados, suas propriedades e métodos relacionados, sem se preocupar com regras de negócio ou persistência.  
  Ela fornece uma base sólida para que outras camadas possam operar de forma consistente e confiável.

- Camada Service:  
  Contém as regras de negócio e a lógica de processamento do sistema.  
  Nessa camada, são implementados os cálculos da folha de pagamento, a aplicação de descontos e proventos, e validações de consistência dos dados.  
  A Service Layer atua como intermediária entre os dados do Model e a apresentação ou controle do fluxo, garantindo que toda a lógica de negócio seja centralizada e reutilizável.

- Camada Repository: 
  Gerencia o acesso e persistência de dados, isolando as operações de leitura e escrita dos arquivos ou banco de dados da lógica de negócio.  
  Isso permite que alterações na forma de armazenamento não impactem outras partes do sistema.  
  Essa camada garante a integridade e consistência dos dados, fornecendo métodos padronizados para consulta, atualização e armazenamento das informações.

- Camada Controller:  
  Serve como intermediária entre o usuário e o sistema, coordenando o fluxo de dados entre as camadas Service e Model.  
  Recebe solicitações, direciona para os serviços apropriados e retorna respostas adequadas, organizando a interação e garantindo que cada operação siga a lógica de negócio definida.  
  Em futuras expansões, essa camada poderá ser responsável por expor endpoints REST para integração com um frontend ou outros sistemas.

- Camada Test:  
  Focada na garantia de qualidade do código, utilizando testes unitários automatizados com JUnit.  
  Verifica a correção dos cálculos, regras de negócio e demais funcionalidades, prevenindo erros e regressões.  
  Essa camada permite validar alterações e novas funcionalidades de forma segura, promovendo maior confiabilidade na manutenção e evolução do sistema.

A arquitetura em camadas adotada proporciona um código modular, organizado e de fácil manutenção, facilitando o entendimento, a reutilização de componentes e a expansão futura do sistema sem comprometer a consistência e qualidade do software.

3. Dados de exemplo

O arquivo `funcionarios.json`, localizado na raiz do projeto, contém um conjunto de funcionários de exemplo que podem ser utilizados para demonstrações e testes do sistema.  

Cada objeto no arquivo representa um funcionário e inclui informações como:  

- id: Identificador único do funcionário.  
- nome, cpf, cargo, telefone, email: Dados pessoais e de contato.  
- endereço: rua, bairro e número.  
- dataNascimento e dataAdmissao: Datas importantes para cálculos e registros.  
- salarioBase: Valor base do salário do funcionário.  
- horasSemanais e diasTrabalhadosMes: Dados para cálculo proporcional de salário.  
- valeTransporte e planoBeneficios: Informações sobre benefícios e descontos aplicáveis.  
- numDependentes e pensaoAlimenticia: Dados que impactam no cálculo de descontos e impostos.

 Exemplo de entrada no funcionarios.json:

json
[
  {
    "id": 0,
    "nome": "Pedro Vieira",
    "cpf": "12345678900",
    "cargo": "Desenvolvedor",
    "telefone": "31999999999",
    "rua": "Rua A",
    "bairro": "Bairro B",
    "numero": 100,
    "email": "pedro@email.com",
    "dataNascimento": "Oct 8, 2025, 12:24:43 AM",
    "dataAdmissao": "Oct 8, 2025, 12:24:43 AM",
    "salarioBase": 3000.0,
    "horasSemanais": 40,
    "diasTrabalhadosMes": 0,
    "valeTransporte": {
      "tipo": "VALE_TRANSPORTE",
      "valor": 150.0,
      "desconto": 180.0
    },
    "planoBeneficios": [
      {"tipo": "VALE_ALIMENTACAO", "valor": 20.0, "desconto": 0.0},
      {"tipo": "PERICULOSIDADE", "valor": 100.0, "desconto": 0.0},
      {"tipo": "VALE_TRANSPORTE", "valor": 150.0, "desconto": 180.0}
    ],
    "numDependentes": 0,
    "pensaoAlimenticia": 0.0
  }
]

4. Como compilar, testar e executar (comandos prontos)

Observação: os comandos abaixo usam o wrapper Maven presente em `backend/folha-facil`, portanto não é obrigatório ter o Maven instalado globalmente.

###  No Linux/macOS:

- Tornar o wrapper executável (uma vez):
  ```bash
  chmod +x backend/folha-facil/mvnw

Compilar e empacotar:
- ./backend/folha-facil/mvnw -f backend/folha-facil clean package

Executar testes automatizados:
- ./backend/folha-facil/mvnw -f backend/folha-facil test

Executar a aplicação (caso o projeto gere um JAR com Main):
- java -jar backend/folha-facil/target/<nome-do-artifact>.jar

No Windows (PowerShell ou cmd):
- backend\folha-facil\mvnw.cmd -f backend\folha-facil clean package
- backend\folha-facil\mvnw.cmd -f backend\folha-facil test


5. Aplicação dos conceitos de POO (Herança, Polimorfismo, Interfaces, Classes Abstratas)

O projeto demonstra os principais conceitos de Programação Orientada a Objetos (POO) de forma prática:

* Herança: A classe `Funcionario` é a base comum para diferentes tipos de vínculos (CLT, PJ, Estagiário).
    Exemplo:
    ```java
    public class FuncionarioCLT extends Funcionario {
        // Implementa regras específicas de CLT, como INSS e IRRF
    }
    ```

* Polimorfismo: O método `calcularFolha()` no serviço aceita diferentes tipos de funcionários, aplicando a lógica correta de acordo com o tipo em tempo de execução.

* Interfaces: A interface `CalculadoraFolha` define o contrato para o cálculo de salários, sendo implementada por classes concretas como `CalculadoraFolhaCLT` e `CalculadoraFolhaPJ`.

* Classes Abstratas: A classe abstrata `Beneficio` contém atributos e métodos comuns a todos os tipos de benefícios (como vale-transporte, alimentação e periculosidade).

Essas implementações estão localizadas principalmente nos pacotes:

* `backend/folha-facil/src/main/java/com/engsoft/folhafacil/model`
* `backend/folha-facil/src/main/java/com/engsoft/folhafacil/service`

6. Testes unitários

Os testes unitários foram implementados com JUnit 5, garantindo a verificação automática das regras de negócio.

* Localização:
    `backend/folha-facil/src/test/java/com/engsoft/folhafacil/`

* Principais testes:
    * `CalculoFolhaServiceTest` $\rightarrow$ valida o cálculo de salário líquido e descontos.
    * `FuncionarioTest` $\rightarrow$ garante que os dados do funcionário sejam lidos corretamente do JSON.
    * `DescontoINSSTest` $\rightarrow$ verifica as faixas de contribuição e arredondamentos.

#### Executar todos os testes:

```bash
./backend/folha-facil/mvnw -f backend/folha-facil test

7. Entregáveis exigidos pela 2ª sprint — checklist 
- [x] Código-fonte atualizado no repositório GitHub (backend/folha-facil)  
- [x] README.md (este arquivo) explicando como compilar, rodar e testar, e indicando onde estão os conceitos de POO aplicados  
- [x] Testes unitários automatizados (localizados em src/test/java)  

8. Observações sobre submissão e avaliação
- Vídeo: máximo 3 minutos; mostre comandos e resultados (build & testes) — caso a apresentação seja presencial, prepare os mesmos passos para execução ao vivo.
- Não subir dados reais dos funcionários (use o funcionarios.json de exemplo ou anonimizado).
- Se solicitado, gerar JavaDoc e anexar link/artefato.

9. Estrutura do Repositório (itens relevantes)

A estrutura do projeto foi organizada para garantir clareza, modularidade e fácil manutenção.  
A seguir, estão descritos os principais diretórios e arquivos que compõem o sistema de folha de pagamento.
calculo-folha-pagamento-teorica-folhafacil/
├── backend/
│ └── folha-facil/
│ ├── mvnw
│ ├── mvnw.cmd
│ ├── pom.xml
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/
│ │ │ │ └── com/
│ │ │ │ └── engsoft/
│ │ │ │ └── folhafacil/
│ │ │ │ ├── model/
│ │ │ │ │ ├── Funcionario.java
│ │ │ │ │ ├── Beneficio.java
│ │ │ │ │ └── Desconto.java
│ │ │ │ ├── service/
│ │ │ │ │ ├── CalculoFolhaService.java
│ │ │ │ │ ├── CalculadoraFolha.java
│ │ │ │ │ └── DescontoINSSService.java
│ │ │ │ ├── controller/
│ │ │ │ │ └── FolhaController.java
│ │ │ │ ├── repository/
│ │ │ │ │ └── FuncionarioRepository.java
│ │ │ │ └── util/
│ │ │ │ └── JsonReader.java
│ │ │ └── resources/
│ │ │ └── funcionarios.json
│ │ └── test/
│ │ └── java/
│ │ └── com/
│ │ └── engsoft/
│ │ └── folhafacil/
│ │ ├── service/
│ │ │ └── CalculoFolhaServiceTest.java
│ │ └── model/
│ │ └── FuncionarioTest.java
│ └── target/
│ └── folha-facil-1.0-SNAPSHOT.jar
├── frontend/
│ ├── index.html
│ ├── css/
│ │ └── style.css
│ └── js/
│ └── main.js
├── docs/
│ └── diagramas/
│ ├── diagrama_classes.png
│ └── diagrama_casos_de_uso.png
└── README.md

##  Descrição das principais partes do repositório

### 🔹 `pom.xml`
Arquivo de configuração do Maven, define as dependências do projeto (JUnit, Gson, etc.), o Java versionado (17), e as metas de build e teste.

### 🔹 `mvnw` e `mvnw.cmd`
Wrappers do Maven que permitem compilar e testar o projeto sem precisar instalar o Maven globalmente.

###  `src/main/java/com/engsoft/folhafacil/`
Código-fonte principal da aplicação.  
Dividido em camadas, seguindo o padrão de arquitetura em camadas (MVC).

- `model/` → Define as entidades centrais do domínio (ex.: `Funcionario`, `Beneficio`, `Desconto`).  
  Responsável por armazenar os atributos e comportamentos dos objetos do sistema.

- `service/` → Contém a lógica de negócio, cálculos e regras de processamento.  
  Exemplo: a classe `CalculoFolhaService` realiza o cálculo do salário líquido aplicando descontos e benefícios.

- `controller/` → Controla o fluxo da aplicação, sendo o ponto central de execução do sistema.  
  Exemplo: `FolhaController.java` faz a leitura dos dados, chama o serviço de cálculo e exibe os resultados.

- `repository/` → Gerencia o acesso aos dados de funcionários e benefícios.  
  Exemplo: `FuncionarioRepository.java` lê e grava informações a partir do arquivo `funcionarios.json`.

- `util/` → Contém classes auxiliares, como `JsonReader.java`, responsável por converter arquivos JSON em objetos Java.
###  `src/main/resources/`
Armazena recursos usados pela aplicação, como arquivos de configuração e dados de exemplo.

- `funcionarios.json` → Arquivo de entrada com a lista de funcionários e suas informações (salário base, benefícios, dependentes, etc.).  
  É utilizado pelo sistema para gerar a folha de pagamento de forma automatizada.

###  `src/test/java/`
Contém os testes unitários automatizados utilizando o JUnit 5.  
Cada pacote espelha a estrutura da aplicação principal, garantindo que todas as classes de negócio sejam testadas.

Exemplo:
- `CalculoFolhaServiceTest.java` → Testa o cálculo correto dos descontos e salário líquido.
- `FuncionarioTest.java` → Testa a integridade e consistência dos dados do funcionário.

###  `frontend/`
Contém uma interface web simples, usada para exibir relatórios e resultados do cálculo da folha.  
Embora o foco do projeto esteja no backend, esta camada demonstra integração com HTML, CSS e JavaScript.

- `index.html` → Página principal da interface.  
- `css/style.css` → Define o estilo visual da aplicação.  
- `js/main.js` → Manipula eventos e faz a comunicação com o backend (futura API REST).

###  `docs/`
Diretório dedicado à documentação técnica e diagramas UML.

- `diagramas/` → Contém o diagrama de classes e o diagrama de casos de uso da aplicação.

Esses arquivos são úteis para a apresentação e explicação da arquitetura do projeto durante a avaliação.

###  `README.md`
Arquivo principal de documentação do projeto (este arquivo), descrevendo:
- Objetivo do sistema  
- Estrutura de pastas  
- Instruções de compilação e execução  
- Conceitos de POO aplicados  
- Testes e design patterns  

###  Resumo da Estrutura

| Diretório / Arquivo | Função Principal |
|----------------------|------------------|
| `backend/` | Código Java e lógica de negócio |
| `frontend/` | Interface visual e relatórios |
| `docs/` | Documentação e diagramas UML |
| `funcionarios.json` | Dados de entrada para cálculos |
| `pom.xml` | Configuração do build e dependências |
| `src/test/java/` | Testes automatizados com JUnit |
| `README.md` | Documentação principal do projeto |

---

10. Design Patterns Utilizados  
Para garantir extensibilidade e reutilização de código, o projeto faz uso de alguns padrões de projeto (Design Patterns):  
- **Strategy:** utilizado no cálculo da folha para permitir múltiplas estratégias de cálculo conforme o tipo de funcionário.  
- **Template Method:** aplicado nas classes abstratas que definem etapas padrão para o cálculo de proventos e descontos.  
- **Factory Method:** usado para criar instâncias de funcionários com base em seu tipo de vínculo (CLT, PJ, Temporário).  

Esses padrões reforçam os princípios de POO e tornam o sistema mais flexível e sustentável a longo prazo.

---

11. Tratamento de Exceções e Validações  
O sistema implementa um tratamento de exceções robusto para garantir execução segura:  
- **Exceções tratadas:** erros de leitura de arquivo JSON, campos ausentes ou inválidos, formatos incorretos e valores fora dos limites esperados.  
- **Validações:** antes do cálculo da folha, o sistema valida dados obrigatórios como nome, cargo, tipo de contrato e salário base.  
- **Logs:** os erros e avisos são registrados em console (ou arquivo, futuramente), facilitando a depuração e manutenção.

12. Créditos   
- Contribuidores:
  - Marcos de Oliveira Antunes
  - Matheus Henrique Tavares Malta
  - Matheus Dias Mendes
  - Davi Vinícius Barbosa de Oliveira
  - Pedro Henrique Santos Vieira
  - Ítalo Vinhas Antunes Silva
  - Artur Costa Cavalcante Coelho
