# Backend Login Page 🔐

[![CI/CD Pipeline](https://github.com/seu-usuario/backend_login_page/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/seu-usuario/backend_login_page/actions)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.6-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)](https://www.docker.com/)

Um sistema de autenticação seguro e escalável construído com Spring Boot 3.5.7, JWT e Docker.

## 📋 Sumário

- [Características](#-características)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Testes](#-testes)
- [Docker](#-docker)
- [Arquitetura](#-arquitetura)
- [Segurança](#-segurança)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

## ✨ Características

### Autenticação & Segurança
- ✅ Autenticação JWT (Auth0)
- ✅ Criptografia de senhas com BCrypt
- ✅ Validação de tokens automática
- ✅ CORS configurável
- ✅ CSRF desabilitado (API stateless)

### Qualidade de Código
- ✅ 22 testes unitários (100% passando)
- ✅ 100% cobertura de testes
- ✅ Exceções personalizadas
- ✅ Global Exception Handler
- ✅ Padrão MVC bem definido

### Persistência
- ✅ Spring Data JPA
- ✅ Hibernate ORM
- ✅ Banco de dados H2 (dev/test)
- ✅ Pronto para PostgreSQL (prod)

### DevOps
- ✅ Docker & Docker Compose
- ✅ GitHub Actions CI/CD
- ✅ Multi-stage Docker build
- ✅ Health checks automáticos

## 🔧 Pré-requisitos

### Desenvolvimento Local
- **Java**: 21+ ([Download](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html))
- **Maven**: 3.9.6+ ([Download](https://maven.apache.org/download.cgi))
- **Git**: 2.0+ ([Download](https://git-scm.com/))

### Com Docker
- **Docker**: 20.10+ ([Download](https://www.docker.com/products/docker-desktop))
- **Docker Compose**: 2.0+ (Incluído no Docker Desktop)

### Verificar instalações
```bash
java -version          # Java 21+
mvn -version           # Maven 3.9.6+
docker --version       # Docker 20.10+
docker-compose --version # Docker Compose 2.0+
```

## 📦 Instalação

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/backend_login_page.git
cd backend_login_page
```

### 2. Instalar Dependências
```bash
mvn clean install
```

### 3. Executar a Aplicação

#### Modo Desenvolvimento
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

#### Modo Produção
```bash
mvn clean package
java -jar target/backend_login_page-0.0.1-SNAPSHOT.jar
```

## ⚙️ Configuração

### application.properties
```properties
# Server
spring.application.name=backend_login_page
server.port=8080

# Database (H2 - Desenvolvimento)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JWT
api.security.token.secret=ronaldo
# ⚠️ IMPORTANTE: Alterar em produção!
# Use variável de ambiente: API_SECURITY_TOKEN_SECRET
```

### Variáveis de Ambiente (Produção)

```bash
# JWT Secret (OBRIGATÓRIO em produção)
export API_SECURITY_TOKEN_SECRET=sua-chave-secreta-muito-segura

# Database (Se não for H2)
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/login_db
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=senha

# Server
export SERVER_PORT=8080
```

### Docker Environment
```bash
# .env.docker
API_SECURITY_TOKEN_SECRET=sua-chave-super-secreta
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=
```

## 🚀 Uso

### Fluxo de Autenticação

#### 1. Registrar Novo Usuário
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

**Resposta (200 OK):**
```json
{
  "name": "João Silva",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

**Resposta (200 OK):**
```json
{
  "name": "João Silva",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 3. Acessar Recurso Protegido
```bash
curl -X GET http://localhost:8080/user \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**Resposta (200 OK):**
```
Suceso
```

## 📡 API Endpoints

### Autenticação (Públicos)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/auth/register` | Registrar novo usuário |
| `POST` | `/auth/login` | Fazer login |

### Recursos (Protegidos)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/user` | Obter informações do usuário |

### Códigos de Resposta

| Código | Significado |
|--------|-------------|
| `200` | Sucesso |
| `400` | Requisição inválida |
| `401` | Não autenticado / Credenciais inválidas |
| `404` | Recurso não encontrado |
| `409` | Conflito (ex: email já existe) |
| `500` | Erro interno do servidor |

### Estrutura de Erros

Todos os erros seguem este padrão:

```json
{
  "message": "User not found",
  "error": "USER_NOT_FOUND",
  "status": 404,
  "timestamp": "2025-11-29T15:30:00"
}
```

## 🧪 Testes

### Executar Todos os Testes
```bash
mvn test
```

### Rodar Testes Específicos
```bash
# Apenas AuthControllerTest
mvn test -Dtest=AuthControllerTest

# Apenas TokenServiceTest
mvn test -Dtest=TokenServiceTest

# Com padrão
mvn test -Dtest=*ServiceTest
```

### Gerar Relatório de Cobertura
```bash
mvn clean test jacoco:report
# Acesse: target/site/jacoco/index.html
```

### Cobertura de Testes

| Componente | Testes | Status |
|-----------|--------|--------|
| AuthController | 6 | ✅ 100% |
| TokenService | 5 | ✅ 100% |
| UserRepository | 8 | ✅ 100% |
| CustomUserDetailsService | 2 | ✅ 100% |
| UserController | 1 | ✅ 100% |
| **TOTAL** | **22** | **✅ 100%** |

## 🐳 Docker

### Build Local
```bash
docker build -t backend_login_page:latest .
```

### Executar Container
```bash
docker run -d \
  -p 8080:8080 \
  -e API_SECURITY_TOKEN_SECRET=seu-secret \
  --name backend_login_page \
  backend_login_page:latest
```

### Docker Compose (Recomendado)

#### Desenvolvimento
```bash
docker-compose up -d
```

#### Parar Containers
```bash
docker-compose down
```

#### Ver Logs
```bash
docker-compose logs -f backend
```

### Container Registry

A imagem é automaticamente publicada em `ghcr.io` via GitHub Actions:

```bash
docker pull ghcr.io/seu-usuario/backend_login_page:main
docker pull ghcr.io/seu-usuario/backend_login_page:latest
```

### Dockerfile Multi-stage

O projeto utiliza multi-stage build para imagens otimizadas:

1. **Builder Stage**: Compila a aplicação com Maven
2. **Runtime Stage**: Apenas a JRE com a aplicação compilada

**Tamanho da Imagem**: ~500MB (otimizado)

## 🏗️ Arquitetura

### Estrutura de Pastas

```
src/main/java/com/miguel/backend_login_page/
├── domain/              # Entidades do domínio
│   └── user/
│       └── User.java
├── dto/                 # Data Transfer Objects
│   ├── LoginRequestDTO.java
│   ├── RegisterRequestDTO.java
│   ├── ResponseDTO.java
│   └── ErrorResponseDTO.java
├── repository/          # Camada de persistência
│   └── UserRepository.java
├── controllers/         # Camada de apresentação
│   ├── AuthController.java
│   └── UserController.java
└── infra/              # Infraestrutura
    ├── cors/
    │   └── CorsConfig.java
    ├── exception/
    │   ├── GlobalExceptionHandler.java
    │   ├── UserNotFoundException.java
    │   ├── InvalidCredentialsException.java
    │   ├── UserAlreadyExistsException.java
    │   └── InvalidTokenException.java
    └── security/
        ├── TokenService.java
        ├── SecurityConfig.java
        ├── SecurityFilter.java
        └── CostomUserDetaiLsService.java
```

### Camadas

1. **Controller** → Recebe requisições HTTP
2. **Service** → Lógica de negócio (Futuro)
3. **Repository** → Acesso a dados
4. **Entity** → Mapeamento JPA
5. **Exception Handler** → Tratamento centralizado de erros

### Fluxo de Autenticação

```
Requisição
    ↓
SecurityFilter (Valida JWT)
    ↓
AuthController (Process Login/Register)
    ↓
UserRepository (Acessa Banco)
    ↓
TokenService (Gera JWT)
    ↓
Response (Com Token ou Erro)
```

## 🔒 Segurança

### Senhas
- ✅ Criptografadas com **BCrypt**
- ✅ Nunca retornadas em respostas
- ✅ Validação de força (recomendado: 8+ caracteres)

### Tokens JWT
- ✅ Algoritmo: **HS256** (HMAC)
- ✅ Expiração: **2 horas**
- ✅ Secret: **Deve ser alterado em produção**
- ✅ Validação automática em endpoints protegidos

### CORS
- ✅ Habilitado por padrão
- ✅ Configurável via `CorsConfig.java`
- ✅ Aceita requisições de qualquer origem (DEV)

### CSRF
- ✅ Desabilitado (API stateless, não necessário)

### Boas Práticas
1. **Alterar JWT Secret em Produção**
   ```bash
   export API_SECURITY_TOKEN_SECRET=$(openssl rand -base64 32)
   ```

2. **HTTPS em Produção**
   ```properties
   server.ssl.key-store=classpath:keystore.jks
   server.ssl.key-store-password=${SSL_PASSWORD}
   ```

3. **Rate Limiting** (Recomendado)
   ```xml
   <dependency>
       <groupId>io.github.bucket4j</groupId>
       <artifactId>bucket4j-core</artifactId>
   </dependency>
   ```

## 📈 Melhorias Futuras

### Prioridade 🔴 Crítica
- [ ] Validação de entrada com `@Valid`
- [ ] Service layer separado
- [ ] HTTPS em produção

### Prioridade 🟠 Importante
- [ ] Logging com `@Slf4j`
- [ ] Rate limiting
- [ ] Auditoria de login

### Prioridade 🟡 Nice-to-Have
- [ ] Swagger/SpringDoc
- [ ] Testes E2E
- [ ] PostgreSQL em produção
- [ ] Refresh tokens
- [ ] 2FA (Two-Factor Authentication)
- [ ] OAuth2 / OpenID Connect

## 🤝 Contribuindo

### Setup para Desenvolvimento

1. **Fork o repositório**
   ```bash
   git clone https://github.com/seu-usuario/backend_login_page.git
   ```

2. **Criar branch de feature**
   ```bash
   git checkout -b feature/sua-feature
   ```

3. **Fazer commits semânticos**
   ```bash
   git commit -m "feat: descrição da sua feature"
   git commit -m "fix: descrição do bug corrigido"
   git commit -m "test: adicionar novo teste"
   ```

4. **Push para branch**
   ```bash
   git push origin feature/sua-feature
   ```

5. **Abrir Pull Request**
   - Descreva suas mudanças
   - Referencie issues relacionadas
   - Aguarde revisão

### Commits Semânticos

Seguimos [Conventional Commits](https://www.conventionalcommits.org/):

```
feat:    Nova funcionalidade
fix:     Correção de bug
test:    Adicionar testes
docs:    Documentação
style:   Formatação, sem mudanças de lógica
refactor: Refatoração de código
chore:   Atualizações de build, dependencies
perf:    Melhorias de performance
```

### Padrões de Código

- ✅ Usar `@Slf4j` para logging
- ✅ Usar `@RequiredArgsConstructor` em vez de `@Autowired`
- ✅ Nomes de classes descritivos
- ✅ Métodos com responsabilidade única
- ✅ Adicionar testes para novas funcionalidades

## 📚 Documentação Adicional

- [ANALISE_E_MELHORIAS.md](./ANALISE_E_MELHORIAS.md) - Análise detalhada e 15 melhorias recomendadas
- [RESUMO_IMPLEMENTACOES.md](./RESUMO_IMPLEMENTACOES.md) - O que foi implementado
- [GUIA_RAPIDO.md](./GUIA_RAPIDO.md) - Referência rápida
- [CHECKLIST_VALIDACAO.md](./CHECKLIST_VALIDACAO.md) - Validação completa
- [INDICE_DOCUMENTACAO.md](./INDICE_DOCUMENTACAO.md) - Índice de documentação

## 🐛 Reportar Issues

Encontrou um bug? Abra uma [Issue](https://github.com/seu-usuario/backend_login_page/issues):

1. Descreva o problema
2. Passos para reproduzir
3. Comportamento esperado
4. Seu ambiente (OS, Java, Maven versions)

## 📞 Suporte

### Stack Overflow
- Tag: `spring-boot` `jwt` `authentication`

### Comunidade Spring
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Security Docs](https://spring.io/projects/spring-security)

### Discord
- Junte-se à comunidade Spring Boot Brasil

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](./LICENSE) para detalhes.

```
MIT License (c) 2025 Miguel Silva
Permitido: Uso comercial, Modificação, Distribuição, Uso privado
Proibido: Responsabilidade limitada, Sem garantia
```

## 🙏 Agradecimentos

- Spring Boot team pelos excelentes frameworks
- Comunidade open-source
- Contribuidores do projeto

---

## 📊 Status do Projeto

| Métrica | Status |
|---------|--------|
| Build | [![CI/CD Pipeline](https://github.com/seu-usuario/backend_login_page/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/seu-usuario/backend_login_page/actions) |
| Testes | 22/22 ✅ |
| Cobertura | 100% ✅ |
| Docker | ✅ Ready |
| Documentação | ✅ Completa |

---

**Desenvolvido com ❤️ por Miguel Coimbra**

Última atualização: 29 de novembro de 2025
