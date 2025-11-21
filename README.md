# Global Solution - Plataforma de Cursos Corporativa

## Descrição
Plataforma de cursos corporativa desenvolvida em Java com Spring Boot. O sistema permite que empresas gerenciem o aprendizado de seus funcionários, criem competições e utilizem IA para recomendações.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Data JPA** (Oracle Database)
- **Spring Security** (Basic Auth)
- **Spring AI** (Gemini)
- **Spring AMQP** (RabbitMQ)
- **Spring Cache**
- **Bean Validation**
- **Swagger / OpenAPI**

## Configuração
1. **Banco de Dados**: O projeto está configurado para usar o Oracle Database da FIAP. Certifique-se de que a VPN ou a rede permite acesso.
2. **IA Generativa**: A chave da API do Gemini já está configurada no `application.yml`.
3. **RabbitMQ**: É necessário ter uma instância do RabbitMQ rodando localmente na porta 5672 ou configurar as credenciais no `application.yml`.

## Como Executar
```bash
mvn clean install
mvn spring-boot:run
```

## Documentação da API
Acesse o Swagger UI em:
`http://localhost:8080/swagger-ui/index.html`

## Endpoints Principais
- `/empresas`: Gerenciamento de empresas.
- `/funcionarios`: Gerenciamento de funcionários.
- `/cursos`: Gerenciamento de cursos.
- `/matriculas`: Matrícula e progresso.
- `/competicoes`: Criação e gestão de competições.
- `/ai`: Recursos de IA (Recomendação e Descrição).

## Deploy
Para deploy em nuvem (ex: Azure, AWS, Heroku):
1. Gere o JAR: `mvn clean package`.
2. Configure as variáveis de ambiente para banco de dados e API Key.
3. Faça o deploy do JAR.

## Credenciais de Teste
- **Usuário**: admin
- **Senha**: admin
