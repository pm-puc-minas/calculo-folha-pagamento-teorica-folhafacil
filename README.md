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
- O arquivo funcionarios.json na raiz contém um conjunto de funcionários de exemplo para usar em demos e testes. Use esse arquivo como entrada para demonstrações que executem cálculos e gerem saídas (console/arquivo).

4. Como compilar, testar e executar (comandos prontos)
Observação: os comandos abaixo usam o wrapper Maven presente em backend/folha-facil, portanto não é obrigatório ter o Maven instalado globalmente.

No Linux/macOS:
- Tornar o wrapper executável (uma vez):
  - chmod +x backend/folha-facil/mvnw

Compilar e empacotar:
- ./backend/folha-facil/mvnw -f backend/folha-facil clean package

Executar testes automatizados:
- ./backend/folha-facil/mvnw -f backend/folha-facil test

Executar a aplicação (caso o projeto gere um JAR com Main):
- java -jar backend/folha-facil/target/<nome-do-artifact>.jar
(Substitua <nome-do-artifact> pelo nome do JAR gerado em backend/folha-facil/target/)

No Windows (PowerShell ou cmd):
- backend\folha-facil\mvnw.cmd -f backend\folha-facil clean package
- backend\folha-facil\mvnw.cmd -f backend\folha-facil test

5. Aplicação dos conceitos de POO (HERANÇA, POLIMORFISMO, INTERFACES, CLASSES ABSTRATAS)
As implementações que demonstram os conceitos pedidos para a 2ª sprint estão localizadas nos pacotes sob o pacote raiz do projeto Java (com.engsoft). Em particular:
- Modelos de domínio (classes que representam funcionário, contrato, provento/desconto) estão sob: backend/folha-facil/src/main/java/com/engsoft/model
- Serviços de cálculo e regras de negócio (interfaces e implementações concretas) estão sob: backend/folha-facil/src/main/java/com/engsoft/service
- Classes utilitárias e abstratas (comportamentos compartilhados entre tipos de funcionário) estão sob: backend/folha-facil/src/main/java/com/engsoft/core ou com/engsoft/abstracts

Observações (para avaliação):
- Herança e polimorfismo: existem classes base/abstratas que definem comportamento genérico e subclasses que especializam o cálculo para diferentes tipos de vínculo (ex.: CLT, PJ, temporário). Isso permite que o mesmo serviço invoque métodos polimórficos sem conhecer o tipo concreto.
- Interfaces: existe uma interface descrita para o contrato de cálculo da folha (por exemplo: CalculadoraFolha) e implementações concretas injetáveis.
- Classes abstratas: utilizadas para extrair lógica comum entre subclasses e evitar duplicação.

(Se a banca exigir os nomes exatos de classes, estes nomes estão nos arquivos em backend/folha-facil/src/main/java/com/engsoft — para facilitar a visualização, abra esses arquivos e, na apresentação, aponte as classes citadas.)

6. Testes unitários
- Framework: JUnit (versão configurada no pom.xml). Possível uso de Mockito para simular dependências.
- Onde estão: backend/folha-facil/src/test/java/ (testes organizados por pacote correspondente às classes testadas).
- Executar todos os testes:
  - ./backend/folha-facil/mvnw -f backend/folha-facil test
- Executar teste(s) específico(s):
  - ./backend/folha-facil/mvnw -f backend/folha-facil -Dtest=NomeDoTeste test

Recomendações de cobertura (já implementadas/para verificação):
- Cálculo de proventos (salário base, horas extras)
- Cálculo de descontos (INSS, IRRF, faltas)
- Casos de borda e regras de arredondamento

7. Entregáveis exigidos pela 2ª sprint — checklist 
- [x] Código-fonte atualizado no repositório GitHub (backend/folha-facil)  
- [x] README.md (este arquivo) explicando como compilar, rodar e testar, e indicando onde estão os conceitos de POO aplicados  
- [x] Testes unitários automatizados (localizados em src/test/java)  

8. Observações sobre submissão e avaliação
- Vídeo: máximo 3 minutos; mostre comandos e resultados (build & testes) — caso a apresentação seja presencial, prepare os mesmos passos para execução ao vivo.
- Não subir dados reais dos funcionários (use o funcionarios.json de exemplo ou anonimizado).
- Se solicitado, gerar JavaDoc e anexar link/artefato.

9. Estrutura do repositório (itens relevantes)
- backend/folha-facil/  
  - mvnw, mvnw.cmd, .mvn/      -> wrapper Maven incluído (não é necessário Maven instalado globalmente)  
  - pom.xml                    -> configuração do projeto Java/Maven  
  - src/main/java/...          -> código-fonte Java (pacote raiz: com.engsoft)  
  - src/test/java/...          -> testes unitários (JUnit)  
- funcionarios.json            -> dados de exemplo (entrada para demo/testes)  
- frontend/                    -> espaço para frontend (se houver)  
- docs/                        -> documentação complementar (diagramas, etc.)



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
