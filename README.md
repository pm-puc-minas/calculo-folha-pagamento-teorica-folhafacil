[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=20401669&assignment_repo_type=AssignmentRepo)

```markdown
# Folha-Fácil

Sistema de apoio para cálculo de folha de pagamento — implementação em Java, com backend modularizado, dados de exemplo e testes automatizados. Este README documenta o que será entregue na 2ª sprint e contém todas as instruções e evidências que a disciplina exige.

Status: pronto para entrega da 2ª sprint (código no diretório backend/folha-facil; wrapper Maven incluído).

1. Sobre o Projeto
Este repositório contém a implementação do módulo de cálculo de folha pensado para uso didático. A implementação está em Java e organizada para facilitar teste, revisão e demonstração das técnicas de modularização e POO exigidas pela disciplina.

2. Estrutura do repositório (itens relevantes)
- backend/folha-facil/  
  - mvnw, mvnw.cmd, .mvn/      -> wrapper Maven incluído (não é necessário Maven instalado globalmente)  
  - pom.xml                    -> configuração do projeto Java/Maven  
  - src/main/java/...          -> código-fonte Java (pacote raiz: com.engsoft)  
  - src/test/java/...          -> testes unitários (JUnit)  
- funcionarios.json            -> dados de exemplo (entrada para demo/testes)  
- frontend/                    -> espaço para frontend (se houver)  
- docs/                        -> documentação complementar (diagramas, etc.)

3. Como compilar, testar e executar (comandos prontos)
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

4. Dados de exemplo
- O arquivo funcionarios.json na raiz contém um conjunto de funcionários de exemplo para usar em demos e testes. Use esse arquivo como entrada para demonstrações que executem cálculos e gerem saídas (console/arquivo).

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

9. Créditos   
- Contribuidores:
  - Marcos de Oliveira Antunes
  - Matheus Henrique Tavares Malta
  - Matheus Dias Mendes
  - Davi Vinícius Barbosa de Oliveira
  - Pedro Henrique Santos Vieira
  - Ítalo Vinhas Antunes Silva
  - Artur Costa Cavalcante Coelho
