# 🎟️ Event Manager

Uma aplicação fullstack para gerenciar eventos com autenticação via Google e GitHub. Desenvolvido com **Spring Boot**, **React**, **TypeScript** e **OAuth2**.

---

## 🚀 Funcionalidades

- Cadastro e autenticação com Google e GitHub (OAuth2)
- Listagem de usuários autenticados
- Integração entre frontend React e backend Spring Boot
- Banco de dados PostgreSQL com Docker

---

## 🛠️ Tecnologias Utilizadas

### Backend:

- Java 21
- Spring Boot
- Spring Security (OAuth2)
- PostgreSQL
- Maven
- Docker

### Frontend:

- React
- TypeScript
- Axios
- CSS Modules

---

## ✅ Pré-requisitos

- [Java 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Node.js 22+](https://nodejs.org/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/) (ou use o wrapper ./mvnw)
- Conta Google e GitHub para autenticação OAuth2

---

## 🔧 Configuração Inicial

### 1. Clone o repositório:

```bash
git clone https://github.com/ChristianSPires/event-manager.git
cd eventmanager
```

### 2. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
DB_USERNAME=postgres
DB_PASSWORD=postgres

GOOGLE_CLIENT_ID=seu_id_google
GOOGLE_CLIENT_SECRET=sua_secret_google

GITHUB_CLIENT_ID=seu_id_github
GITHUB_CLIENT_SECRET=sua_secret_github
```

### 🐘 Subir o banco de dados com Docker

```bash
docker run --name postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=eventdb \
  -p 5432:5432 \
  -d postgres
```

### 🔙 Rodar o Backend (Spring Boot)

Acesse a pasta do backend:

```bash
cd backend
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

> O backend estará disponível em `http://localhost:8080`

---

### 🔜 Rodar o Frontend (React + TypeScript)

Acesse a pasta do frontend:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Inicie a aplicação:

```bash
npm start
```

> O frontend será aberto automaticamente em `http://localhost:3000`

---

### 🔐 Login OAuth2

Após iniciar a aplicação, clique no botão de login para entrar com sua conta do Google ou GitHub. O backend se encarregará de validar o usuário e armazenar seus dados.

---

## 📂 Estrutura de Pastas

```bash
eventmanager/
│
├── backend/              # Spring Boot application
│   ├── config/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── repository/
│   └── resources/
│
├── frontend/             # React + TypeScript
│   ├── public/
│   └── src/
│
└── .env                  # Variáveis de ambiente
```

---

## 📌 Observações

- O backend busca as variáveis do `.env` automaticamente com o `DotenvLoader`.
- Os dados do banco são persistidos apenas em container local.
- Pode ser usado Docker Compose futuramente para unificar os serviços.
- Código limpo e modular para fácil expansão.
