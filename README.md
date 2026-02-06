<div align="center">

# 📋 Java-TaskManager

**Aplicação web fullstack de gerenciamento de tarefas**, desenvolvida com **Spring Boot** no backend e **React** no frontend.

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen?style=for-the-badge&logo=spring&logoColor=white)
![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge&logo=react&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-6-purple?style=for-the-badge&logo=vite&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

</div>

---

## 📖 Sobre o Projeto

O **Java-TaskManager** é uma aplicação web completa para gerenciamento de tarefas pessoais. Permite que usuários se cadastrem, façam login e gerenciem suas tarefas de forma prática e organizada, com funcionalidades de busca, filtros, categorias e paginação.

---

## ✨ Funcionalidades

### Autenticação & Usuários
- 🔐 Login e cadastro de usuários
- 🍪 Autenticação via JWT armazenado em cookie HttpOnly
- 👤 Visualização e edição do perfil (nome e e-mail)
- 🗑️ Exclusão de conta (com remoção cascata de todas as tarefas)
- 📧 Recuperação de senha via e-mail (token com validade de 1 hora)

### Tarefas
- ➕ Criação de tarefas com título, descrição, categoria e status
- ✏️ Edição e exclusão de tarefas (com verificação de propriedade)
- 📂 **Categorias:** Trabalho, Pessoal, Estudos, Saúde, Outros
- 📊 **Status:** Não Iniciado, Em Andamento, Concluído, Pendente
- 🔍 Busca global ou por campo específico (título, descrição, categoria, status)
- 📄 Paginação server-side com botão "Carregar mais"

---

## 🏗️ Arquitetura

```
Java-TaskManager/
├── backend-taskmanager/    → API REST (Spring Boot)
└── frontend-taskmanager/   → SPA (React + Vite)
```

O frontend (porta `5173`) se comunica com o backend (porta `8080`) via requisições REST. A autenticação é gerenciada por cookies HttpOnly com tokens JWT, enviados automaticamente pelo Axios com `withCredentials: true`.

---

## 🛠️ Tech Stack

| Camada | Tecnologias |
|---|---|
| **Backend** | Java 21, Spring Boot 3.3.3, Spring Security 6, Spring Data JPA, Hibernate |
| **Autenticação** | Auth0 java-jwt 4.4.0, BCrypt |
| **Banco de Dados** | H2 (desenvolvimento) / PostgreSQL (produção) |
| **E-mail** | Spring Boot Mail (SMTP Gmail) |
| **Build (Back)** | Maven |
| **Frontend** | React 19, Vite 6 |
| **UI** | React Bootstrap 2.10, Bootstrap 5.3, Lucide React |
| **HTTP Client** | Axios |
| **Roteamento** | React Router DOM 7 |
| **Estado** | React Context API |

---

## 📁 Estrutura do Projeto

### Backend (`backend-taskmanager/`)

```
src/main/java/edu/taskmanager/taskmanager/
├── TaskmanagerApplication.java   # Classe principal
├── config/                       # DataSeeder (dados iniciais)
├── controllers/                  # AuthController, UserController
├── domain/                       # Entidades: User, Task, PasswordResetToken
├── dto/                          # DTOs de request/response
├── infra/                        # Segurança (JWT, filtros, CORS)
├── repositories/                 # Repositórios JPA
├── services/                     # Lógica de negócio
└── specification/                # Specifications (busca e filtros)
```

### Frontend (`frontend-taskmanager/`)

```
src/
├── App.jsx                       # Rotas da aplicação
├── main.jsx                      # Ponto de entrada
├── context/
│   └── AuthContext.jsx           # Contexto de autenticação
├── components/
│   └── RotaProtegida.jsx         # Wrapper de rotas protegidas
├── pages/
│   ├── login/                    # Tela de login
│   ├── cadastro/                 # Tela de cadastro
│   ├── tela_inicial/             # Dashboard principal com lista de tarefas
│   ├── nova_tarefa/              # Formulário de criação de tarefa
│   ├── visualizar_tarefa/        # Visualização/edição de tarefa
│   ├── visualizar_perfil/        # Perfil do usuário
│   ├── mudar_senha/              # Solicitação de redefinição de senha
│   └── nova_senha/               # Formulário de nova senha
└── services/
    └── api.js                    # Instância Axios configurada
```

---

## 🔗 Endpoints da API

### Autenticação (`/auth`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Login com e-mail e senha; retorna cookie JWT |
| `POST` | `/auth/register` | Cadastro de novo usuário; retorna cookie JWT |
| `GET` | `/auth/status` | Verifica se o usuário está autenticado |

### Usuário (`/user`)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/user` | Retorna dados do usuário autenticado |
| `PUT` | `/user` | Atualiza nome e e-mail do usuário |
| `DELETE` | `/user` | Exclui conta e todas as tarefas associadas |
| `POST` | `/user/password-reset` | Envia e-mail com link de redefinição de senha |
| `POST` | `/user/reset/{token}` | Redefine a senha usando o token recebido por e-mail |

### Tarefas (`/user/tasks`)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/user/tasks` | Lista tarefas com busca, filtro e paginação |
| `POST` | `/user/tasks` | Cria uma nova tarefa |
| `PUT` | `/user/tasks/{taskId}` | Atualiza uma tarefa existente |
| `DELETE` | `/user/tasks/{taskId}` | Exclui uma tarefa |

> **Parâmetros de busca em `GET /user/tasks`:**
> - `query` — Texto de busca
> - `parameterSearch` — Campo de busca: `all`, `title`, `description`, `category`, `status`
> - `page` — Número da página (padrão: 0)
> - `size` — Tamanho da página (padrão: 10)

---

## 🗃️ Modelo de Dados

### User

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | UUID (String) | Identificador único |
| `name` | String | Nome do usuário |
| `email` | String | E-mail (usado como login) |
| `password` | String | Senha criptografada (BCrypt) |
| `tarefas` | List\<Task\> | Tarefas do usuário (One-to-Many) |

### Task

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | UUID (String) | Identificador interno |
| `uuid` | UUID | Identificador público (exposto na API) |
| `title` | String | Título da tarefa |
| `description` | String | Descrição da tarefa |
| `status` | String | Status atual |
| `category` | String | Categoria da tarefa |
| `createdDate` | Date | Data de criação |
| `user` | User | Proprietário (Many-to-One) |

### PasswordResetToken

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Identificador |
| `token` | String | Token UUID para redefinição |
| `user` | User | Usuário associado |
| `expiryDate` | LocalDateTime | Validade (1 hora) |

---

## 🚀 Como Executar

### Pré-requisitos

- **Java 21** (JDK)
- **Maven** (ou use o wrapper `mvnw` incluso)
- **Node.js 18+** e **npm**

### Backend

```bash
# Acesse a pasta do backend
cd backend-taskmanager

# Execute com o Maven Wrapper
./mvnw spring-boot:run
```

O servidor será iniciado em `http://localhost:8080`.

> **Console H2:** acesse `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, sem senha).

### Frontend

```bash
# Acesse a pasta do frontend
cd frontend-taskmanager

# Instale as dependências
npm install

# Inicie o servidor de desenvolvimento
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`.

---

## ⚙️ Configuração

### Variáveis de Ambiente (Backend)

As configurações estão em `backend-taskmanager/src/main/resources/application.properties`:

| Configuração | Descrição | Valor Padrão |
|---|---|---|
| `api.security.token.secret` | Chave para assinatura JWT | `my-secret-key` |
| `spring.mail.username` | E-mail para envio SMTP | `taskmanagerteste@gmail.com` |
| `spring.mail.password` | Senha de app do Gmail | *(não versionada)* |

### Banco de Dados em Produção

Para usar **PostgreSQL**, descomente as linhas correspondentes no `application.properties` e comente as configurações do H2:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=taskmanager_user
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

> Adicione também a dependência do MySQL/PostgreSQL no `pom.xml` (já disponível comentada no arquivo).

---

## 🔒 Segurança

- **Senhas** criptografadas com **BCrypt**
- **Tokens JWT** assinados com HMAC256, expiração de **2 horas**
- Cookies **HttpOnly** (proteção contra XSS)
- **Sessão stateless** no servidor
- **CSRF desabilitado** (proteção via JWT)
- **CORS** configurado para permitir apenas a origem do frontend
- **Verificação de propriedade** nas operações de tarefas

---

## 🧑‍💻 Usuário Padrão (Desenvolvimento)

Na inicialização, um **DataSeeder** cria automaticamente um usuário de teste:

| Campo | Valor |
|---|---|
| E-mail | `joao@gmail.com` |
| Senha | `123` |

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
