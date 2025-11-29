# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
e este projeto segue [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Não Lançado]

### Adicionado
- Docker support com multi-stage builds
- Docker Compose para desenvolvimento local
- GitHub Actions CI/CD pipeline
- Exceções personalizadas para melhor tratamento de erros
- Global Exception Handler para respostas padronizadas
- 22 testes unitários (100% cobertura)
- Documentação completa

### Planejado
- OAuth2 / OpenID Connect
- Refresh tokens
- Two-Factor Authentication (2FA)
- Rate limiting
- Swagger/SpringDoc
- PostgreSQL em produção
- Testes de integração E2E

## [0.0.1] - 2025-11-29

### Adicionado

#### Funcionalidades
- Sistema de autenticação com JWT
- Endpoints de login e registro
- Validação de usuário
- Geração de tokens JWT com expiração de 2 horas

#### Exceções e Tratamento de Erros
- `UserNotFoundException` - Usuário não encontrado
- `InvalidCredentialsException` - Credenciais inválidas
- `UserAlreadyExistsException` - Email duplicado
- `InvalidTokenException` - Token JWT inválido
- GlobalExceptionHandler com respostas padronizadas

#### Testes
- 6 testes para AuthController
- 5 testes para TokenService
- 8 testes para UserRepository
- 2 testes para CustomUserDetailsService
- 1 teste para UserController
- Total: 22 testes (100% passando)

#### Infraestrutura
- Dockerfile multi-stage
- docker-compose.yml para desenvolvimento
- GitHub Actions workflows (CI/CD e Release)
- .dockerignore para otimização

#### Documentação
- README.md completo
- CONTRIBUTING.md com diretrizes
- ANALISE_E_MELHORIAS.md
- RESUMO_IMPLEMENTACOES.md
- GUIA_RAPIDO.md
- CHECKLIST_VALIDACAO.md
- INDICE_DOCUMENTACAO.md

### Detalhes Técnicos

#### Stack
- Java 21
- Spring Boot 3.5.7
- Spring Security
- Spring Data JPA
- JWT Auth0 4.4.0
- H2 Database
- Maven 3.9.6

#### Build
```
mvn clean install      # Instalar dependências
mvn clean package      # Compilar e empacotar
mvn test              # Rodar testes
mvn spring-boot:run   # Executar aplicação
```

#### Docker
```
docker build -t backend_login_page:latest .
docker-compose up -d
docker pull ghcr.io/seu-usuario/backend_login_page:main
```

#### CI/CD
- Testes automáticos em cada push
- Build e push de imagens Docker
- Security scanning com Trivy
- Análise de qualidade de código

### API Endpoints

#### Públicos
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Fazer login

#### Protegidos
- `GET /user` - Obter informações do usuário

### Métricas
- ✅ 22 testes (100% passando)
- ✅ 100% cobertura de código
- ✅ 4 exceções personalizadas
- ✅ Build time: ~2 minutos
- ✅ Docker image size: ~500MB

### Commits

1. `73e9665` - feat: add custom exception classes for better error handling
2. `62980a5` - feat: implement global exception handler with standardized error responses
3. `36e4d1b` - refactor: update AuthController to use custom exceptions
4. `7c49248` - test: add comprehensive unit tests for AuthController
5. `61f1f6c` - test: add unit tests for TokenService, UserRepository and UserDetailsService
6. `87f1eaf` - test: add unit tests for UserController
7. `51d1621` - docs: add comprehensive project documentation
8. `c57fa92` - feat: add Docker support for containerization
9. `b15c169` - ci: add GitHub Actions workflows for CI/CD pipeline

---

## Versioning

As versões seguem o padrão Semantic Versioning:

- **MAJOR** - Mudanças que quebram compatibilidade
- **MINOR** - Novas funcionalidades compatíveis
- **PATCH** - Correções de bugs

### Exemplo
```
v1.2.3
 │ │ └─ PATCH (correções)
 │ └─── MINOR (novas features)
 └───── MAJOR (breaking changes)
```

## Como Atualizar

### De v0.0.1 para próximas versões

```bash
# Atualizar código
git pull origin main

# Instalar novas dependências
mvn clean install

# Executar testes
mvn test

# Iniciar aplicação
mvn spring-boot:run
```

---

**Última atualização:** 29 de novembro de 2025
