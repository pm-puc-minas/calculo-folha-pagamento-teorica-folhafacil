[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=20401669&assignment_repo_type=AssignmentRepo)

# Folha-Facil
Um projeto sobre sistema de gestão de folha de pagamento para um software de gestão de recursos humanos de uma empresa

# Ambiente Folha Fácil

**Responsável:** Artur Coelho  
**Data da alteração:** 26/09  

---

## ⚙️ Configuração do Ambiente

> Faça tudo a partir da branch `main` do projeto.

---

## 🖥️ Frontend

### 1. Instalação do Node via NVM

Instale o **NVM** para facilitar o controle de versão do Node.js:  
🔗 [https://github.com/coreybutler/nvm-windows/releases](https://github.com/coreybutler/nvm-windows/releases)

Após instalar, abra o **CMD** e execute:

```bash
nvm install 22.14.0
```

Verifique se a versão foi instalada:

```bash
nvm ls
```

Selecione a versão desejada:

```bash
nvm use 22.14.0
```

---

### 2. Instalação do Angular e dependências

Abra o projeto no CMD na rota:

```
calculo-folha-pagamento-teorica-folhafacil\frontend\folhafacil
```

Instale a **CLI do Angular** e as dependências do projeto:

```bash
npm install
```

Inicie o projeto:

```bash
ng serve
```

> Esse comando será usado sempre que quiser iniciar o frontend.

O projeto web estará disponível em:  
🌐 [http://localhost:4200/](http://localhost:4200/)

---

## ☕ Backend

### 1. Instalação do Java 17

Baixe o **Java 17**:  
🔗 [https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### 2. Instalação do Maven

Baixe o **Maven**:  
🔗 [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

---

### 3. Variáveis de Ambiente

Abra as **variáveis de ambiente** do seu PC e adicione:

- `JAVA_HOME` → caminho onde o Java foi instalado  
- `M2_HOME` → caminho onde o Maven foi instalado  

Em **Path**, adicione o `/bin` de ambos.

---

### 4. Build do Backend

Abra o CMD na rota:

```
C:\calculo-folha-pagamento-teorica-folhafacil\backend\folhafacil
```

E execute:

```bash
mvn clean install
```

Se os testes retornarem erro, use:

```bash
mvn clean install -DskipTests
```

Configure seu editor de código para rodar o **Spring Boot**, ou rode manualmente.

---

## 🔐 Keycloak (Docker)

### 1. Instalação do Docker

Baixe e instale o **Docker Desktop**:  
🔗 [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)

---

### 2. Instalação do WSL (se necessário)

Se não possuir o **WSL**, instale via PowerShell:

```bash
wsl --install
```

ou

```bash
wsl --update
```

> ⚠️ Caso seu computador não tenha a virtualização ligada, será necessário ativá-la na BIOS.  
> Para quem joga jogos da Riot, pode ser necessário ligar e desligar essa opção (o *Vanguard* não roda com ela ativada).

---

### 3. Subindo o ambiente com Docker

Na pasta raiz do projeto:

```
C:\calculo-folha-pagamento-teorica-folhafacil
```

Execute o comando:

```bash
docker-compose up -d
```

> Esse comando só precisa ser executado uma vez.  
> Nas próximas vezes, basta iniciar o Docker pelo aplicativo.

---

🟢 **Pronto!**  
Seu ambiente **Folha Fácil** estará configurado e pronto para uso.




**Video da aplicação Rodando**

https://youtu.be/b9YxsdeF5Y0
