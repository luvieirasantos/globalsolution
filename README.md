# Global Solution - Plataforma de Cursos Corporativos

Sistema de gerenciamento de cursos corporativos com foco em energia sustentável, desenvolvido com Spring Boot e recursos de IA generativa.

## 🌐 Video
https://youtu.be/b3RP9k4cexU

## 🌐 Aplicação em Produção

**URL:** https://globalsolution-66v2.onrender.com

### Endpoints em Produção

| Endpoint | URL |
|----------|-----|
| Swagger UI | https://globalsolution-66v2.onrender.com/swagger-ui/index.html |
| Cursos | https://globalsolution-66v2.onrender.com/cursos |
| Empresas | https://globalsolution-66v2.onrender.com/empresas |
| Funcionários | https://globalsolution-66v2.onrender.com/funcionarios |
| Matrículas | https://globalsolution-66v2.onrender.com/matriculas |
| Competições | https://globalsolution-66v2.onrender.com/competicoes |
| IA Recomendação | https://globalsolution-66v2.onrender.com/ai/recommendation?topic=Java |
| IA Descrição | https://globalsolution-66v2.onrender.com/ai/course-description?courseName=Spring |

**Autenticação:** Basic Auth (admin/admin)

## Tecnologias

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA** (Oracle Database)
- **Spring Security** (Basic Auth)
- **Spring AI** (OpenAI GPT-4o-mini)
- **Spring AMQP** (RabbitMQ)
- **Spring Cache**
- **Spring HATEOAS**
- **Bean Validation**
- **Swagger / OpenAPI**
- **Internacionalização** (PT-BR / EN)

## Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker (para RabbitMQ)

## Configuração Local

### 1. Clonar o repositório

```bash
git clone https://github.com/luvieirasantos/globalsolution.git
cd globalsolution
```

### 2. Configurar variáveis de ambiente

Copie o arquivo de exemplo e configure suas credenciais:

```bash
cp .env.example .env
```

Edite o arquivo `.env`:

```env
DATABASE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
DATABASE_USERNAME=RM558935
DATABASE_PASSWORD=310805
OPENAI_API_KEY=sua-chave-openai-aqui
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
PORT=8080
```

### 3. Iniciar RabbitMQ com Docker

1 - Abra o docker

```bash
docker run -d \
  --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3-management
```

**Acesso ao RabbitMQ Management:**
- URL: http://localhost:15672
- Usuário: guest
- Senha: guest

### 4. Executar a aplicação

```bash
mvn spring-boot:run
```


## Acessos

| Serviço | URL |
|---------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| RabbitMQ | http://localhost:15672 |
| H2 Console | http://localhost:8080/h2-console |

## Autenticação

A API usa **Basic Authentication**:

- **Usuário**: admin
- **Senha**: admin

Exemplo com cURL:

```bash
curl -u admin:admin http://localhost:8080/cursos
```

## Endpoints Principais

### Cursos
- `GET /cursos` - Listar cursos (paginado)
- `GET /cursos/{id}` - Buscar por ID
- `POST /cursos` - Criar curso
- `PUT /cursos/{id}` - Atualizar curso
- `DELETE /cursos/{id}` - Deletar curso

### Funcionários
- `GET /funcionarios` - Listar funcionários
- `POST /funcionarios` - Criar funcionário

### Empresas
- `GET /empresas` - Listar empresas
- `POST /empresas` - Criar empresa

### Matrículas
- `GET /matriculas` - Listar matrículas
- `POST /matriculas` - Criar matrícula

### Competições
- `GET /competicoes` - Listar competições
- `POST /competicoes` - Criar competição

### IA Generativa
- `GET /ai/recommendation?topic={tema}` - Recomendações de estudo
- `GET /ai/course-description?courseName={nome}` - Gerar descrição de curso

## Deploy em Produção

### Render

1. Crie uma conta em [render.com](https://render.com)
2. Conecte seu repositório GitHub
3. Configure as variáveis de ambiente:

| Variável | Valor |
|----------|-------|
| SPRING_PROFILES_ACTIVE | prod |
| DATABASE_URL | jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL |
| DATABASE_USERNAME | RM558935 |
| DATABASE_PASSWORD | 310805 |
| OPENAI_API_KEY | sua-chave |

4. Build Command: `mvn clean package -DskipTests`
5. Start Command: `java -jar -Dspring.profiles.active=prod target/*.jar`

### Docker

```bash
# Build da imagem
docker build -t globalsolution .

# Executar com docker-compose
docker-compose up -d
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/fiap/globalsolution/
│   │   ├── config/          # Configurações (Security, Cache, RabbitMQ)
│   │   ├── controller/      # Controllers REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Tratamento de exceções
│   │   ├── messaging/       # Producers e Consumers RabbitMQ
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositórios Spring Data
│   │   └── service/         # Serviços de negócio
│   └── resources/
│       ├── application.yml           # Config local
│       ├── application-prod.yml      # Config produção
│       ├── messages.properties       # i18n Português
│       └── messages_en.properties    # i18n Inglês
```

## Funcionalidades

- [x] CRUD completo de entidades
- [x] Autenticação com Spring Security
- [x] Validação com Bean Validation
- [x] Paginação e HATEOAS
- [x] Cache de consultas
- [x] Internacionalização (PT/EN)
- [x] Mensageria assíncrona (RabbitMQ)
- [x] IA Generativa (OpenAI)
- [x] Tratamento global de exceções
- [x] Documentação Swagger/OpenAPI
- [x] Pronto para deploy em nuvem

## Integrantes

- Lu Vieira Santos - RM558935
- Melissa Perreira
- Diego Furigo


## Licença

Este projeto foi desenvolvido para fins acadêmicos - FIAP 2024.
