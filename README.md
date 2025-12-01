# Backend Login Page 🔐

[![CI/CD Pipeline](https://github.com/your-username/backend_login_page/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/your-username/backend_login_page/actions)
[![Java](https://img.shields.io/badge/Java-21-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.6-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)](https://www.docker.com/)

A secure and extensible authentication microservice built with Spring Boot 3.5.7, JWT and Docker.

## Table of Contents

- Features
- Requirements
- Installation
- Configuration
- Usage
- API Endpoints
- Tests
- Docker
- Architecture
- Security
- Contributing
- License

## Features

- JWT authentication and authorization
- Password hashing with BCrypt
- Spring Security integration
- JPA + Hibernate persistence
- In-memory H2 for tests and local development
- Prepared for PostgreSQL (docker-compose)
- Unit tests and CI pipeline

## Requirements

- Java 21+
- Maven 3.9+
- Docker (optional for containers)

## Quick Start (local)

1. Clone the repository

```bash
git clone https://github.com/your-username/backend_login_page.git
cd backend_login_page
```

2. Build

```bash
mvn clean package
```

3. Run (development)

```bash
mvn spring-boot:run
```

Application will run on `http://localhost:8080`.

### Using Docker Compose (Postgres)

1. Copy the example env file and update secrets:

```bash
cp .env.example .env
# edit .env and set secure values
```

2. Start services

```bash
docker compose up -d
```

3. Stop services

```bash
docker compose down
```

## Configuration

Environment variables are used for secrets and database settings. See `.env.example`.

Important variables:

- `API_SECURITY_TOKEN_SECRET` - JWT secret
- `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB` - Postgres credentials
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` - Spring datasource

## API Endpoints

Authentication:

- `POST /auth/register` - register a new user
- `POST /auth/login` - login and receive a JWT token

Protected resources (require `Authorization: Bearer <token>`):

- `GET /user` - returns a simple protected resource

## Tests

Run all tests:

```bash
mvn test
```

Run a single test class:

```bash
mvn -Dtest=AuthControllerTest test
```

## Contributing

See `CONTRIBUTING.md` for contribution guidelines.

## License

This project uses a permissive license. See the `LICENSE` file if present.

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
