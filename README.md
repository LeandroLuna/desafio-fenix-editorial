# Desafio Fenix Editorial

Teste técnico para desenvolvedor na empresa Fenix Editorial.

## Frontend (Angular) - Plataforma de Cursos Online

Uma plataforma responsiva para visualização e interação com cursos online, oferecendo uma experiência intuitiva para alunos explorarem diferentes cursos.

Deploy: [Online Course Platform - Vercel](https://onlinecourseplatform-gamma.vercel.app/courses)

### Tecnologias utilizadas
- Angular 17
- NgRx (Gerenciamento de Estado)
- Angular Material
- TypeScript
- Jasmine/Karma (Testes)
- Docker

### Como executar

#### Opção 1: Execução local

1. Instalar dependências:
```bash
cd frontend
npm install
```

2. Executar o projeto:
```bash
ng serve
```

3. Executar testes:
```bash
ng test
```

#### Opção 2: Docker (Recomendado)

1. Construir e iniciar os containers:
```bash
cd frontend
docker-compose up --build
```

O frontend estará disponível em: `http://localhost:4200`

## Backend (Spring Boot) - Sistema de Gerenciamento de Cursos e Matrículas

API REST para gerenciamento completo de cursos, alunos e matrículas, com autenticação JWT e documentação Swagger integrada.

### Tecnologias utilizadas
- Java 17
- Spring Boot 3.4.2
- Spring Security + JWT
- H2 Database
- JUnit 5
- Swagger/OpenAPI
- Lombok
- Docker
- Maven

### Como executar

#### Opção 1: Execução Local

1. Compilar o projeto:
```bash
cd backend
mvn clean install
```

2. Executar o projeto:
```bash
mvn spring-boot:run
```

#### Opção 2: Docker (Recomendado)

1. Construir e iniciar os containers:
```bash
cd backend
docker-compose up --build
```

O backend estará disponível em: `http://localhost:8080`

### Documentação da API
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Coleção das rotas da API no Postman: `Fenix Editorial.postman_collection.json` (disponível na raiz do projeto)

### Credenciais padrão
```
Usuário: admin
Senha: admin123
```

### Banco de Dados
- Console H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:course_enrollment_system_db`
- Usuário: `sa`
- Senha: ` ` (em branco)

## Requisitos implementados

### Frontend
- ✅ Listagem de Cursos
- ✅ Filtros e Ordenação
- ✅ Pesquisa
- ✅ Detalhes do Curso
- ✅ Responsividade
- ✅ Estado Global (NgRx)
- ✅ Testes Automatizados

### Backend
- ✅ CRUD completo (Cursos, Alunos, Matrículas)
- ✅ Relacionamentos entre entidades
- ✅ Validações de campos e regras de negócio
- ✅ Autenticação JWT
- ✅ Documentação OpenAPI/Swagger
- ✅ Testes unitários e de integração
- ✅ Containerização com Docker

## Observações
- O sistema utiliza banco de dados em memória (H2) para facilitar testes e execução
- Todos os endpoints da API requerem autenticação (exceto /api/auth/login). A autenticação é feita através de JWT. 
- - Para testar a aplicação, pode-se utilizar as credenciais padrão (Usuário: admin, Senha: admin123). 
- - Acesse a rota `/api/auth/login` para obter o token de autenticação das demais rotas.
- Os dados são resetados ao reiniciar a aplicação.
- O frontend não está conectado ao backend.
