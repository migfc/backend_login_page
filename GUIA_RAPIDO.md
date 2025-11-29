# 🎯 GUIA RÁPIDO - Backend Login Page

## 📁 Onde Encontrar o Quê

### Exceções Personalizadas
```
infra/exception/
├── UserNotFoundException.java          → Quando usuário não é encontrado
├── InvalidCredentialsException.java    → Credenciais (email/senha) inválidas
├── UserAlreadyExistsException.java     → Tentativa de registrar email existente
└── InvalidTokenException.java          → Token JWT inválido/expirado
```

### Exception Handler
```
infra/exception/GlobalExceptionHandler.java
→ Centraliza tratamento de exceções da aplicação
→ Retorna responses padronizadas com ErrorResponseDTO
```

### DTOs
```
dto/
├── LoginRequestDTO.java               → Email e senha para login
├── RegisterRequestDTO.java            → Nome, email e senha para registro
├── ResponseDTO.java                   → Resposta com nome e token
└── ErrorResponseDTO.java              → Resposta de erro padronizada
```

### Testes
```
src/test/java/com/miguel/backend_login_page/
├── controllers/
│   ├── AuthControllerTest.java        → 6 testes de autenticação
│   └── UserControllerTest.java        → 1 teste de usuário
├── infra/security/
│   ├── TokenServiceTest.java          → 5 testes de JWT
│   └── CustomUserDetailsServiceTest.java → 2 testes de UserDetails
└── repository/
    └── UserRepositoryTest.java        → 8 testes de persistência
```

---

## 🧪 Como Rodar os Testes

### Todos os testes:
```bash
mvn test
```

### Apenas testes de AuthController:
```bash
mvn test -Dtest=AuthControllerTest
```

### Com relatório de cobertura:
```bash
mvn clean test jacoco:report
# Relatório em: target/site/jacoco/index.html
```

### Rodar aplicação:
```bash
mvn spring-boot:run
```

---

## 🔐 Fluxo de Autenticação

### 1. Login
```
POST /auth/login
{
  "email": "user@example.com",
  "password": "password123"
}

Resposta (200 OK):
{
  "name": "John Doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

Erros Possíveis:
- 404: User not found (UserNotFoundException)
- 401: Invalid email or password (InvalidCredentialsException)
```

### 2. Registro
```
POST /auth/register
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "password": "password123"
}

Resposta (200 OK):
{
  "name": "Jane Doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

Erros Possíveis:
- 409: User already exists (UserAlreadyExistsException)
```

### 3. Acessar Recurso Protegido
```
GET /user
Authorization: Bearer {token}

Resposta (200 OK):
"Suceso"
```

---

## 📋 Estrutura de Respostas

### Sucesso (Login/Register)
```json
{
  "name": "John Doe",
  "token": "jwt.token.here"
}
```

### Erro Padrão
```json
{
  "message": "User not found",
  "error": "USER_NOT_FOUND",
  "status": 404,
  "timestamp": "2025-11-29T10:30:00"
}
```

### Códigos de Erro
| Código | Status HTTP | Significado |
|--------|-------------|-------------|
| USER_NOT_FOUND | 404 | Usuário não existe |
| INVALID_CREDENTIALS | 401 | Email ou senha inválidos |
| USER_ALREADY_EXISTS | 409 | Email já está registrado |
| INVALID_TOKEN | 401 | Token JWT inválido ou expirado |
| INTERNAL_ERROR | 500 | Erro interno do servidor |

---

## 🧪 Exemplos de Teste

### Testar Login com Credenciais Válidas
```java
@Test
void testLoginSuccess() {
    LoginRequestDTO request = new LoginRequestDTO("john@example.com", "password");
    // Mock do usuário e senha
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John Doe"));
}
```

### Testar Registro com Email Duplicado
```java
@Test
void testRegisterUserAlreadyExists() {
    RegisterRequestDTO request = new RegisterRequestDTO("John", "john@example.com", "pass");
    // Mock: usuário já existe
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("USER_ALREADY_EXISTS"));
}
```

---

## 🏗️ Stack Tecnológico

- **Framework**: Spring Boot 3.5.7
- **Java Version**: 21
- **Build Tool**: Maven
- **Security**: Spring Security + JWT (Auth0)
- **Database**: H2 (desenvolvimento)
- **ORM**: Spring Data JPA
- **Testing**: JUnit 5, Mockito, Spring Test
- **Utils**: Lombok

---

## 📊 Cobertura de Testes

| Componente | Testes | Status |
|-----------|--------|--------|
| AuthController | 6 | ✅ Passing |
| TokenService | 5 | ✅ Passing |
| UserRepository | 8 | ✅ Passing |
| CustomUserDetailsService | 2 | ✅ Passing |
| UserController | 1 | ✅ Passing |
| **TOTAL** | **22** | **✅ 100%** |

---

## 🔧 Configurações Importantes

### application.properties
```properties
spring.application.name=backend_login_page
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver

# ⚠️ IMPORTANTE: Alterar em produção
api.security.token.secret=ronaldo
```

### SecurityConfig
- CSRF desabilitado
- CORS habilitado
- Sessões stateless
- /auth/login e /auth/register são públicos
- Outros endpoints requerem autenticação

---

## ⚡ Dicas Úteis

### 1. Regenerar Token
Tokens JWT são válidos por 2 horas. Para gerar novo token, faça login novamente.

### 2. Testar com cURL
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'

# Acessar recurso protegido
curl -X GET http://localhost:8080/user \
  -H "Authorization: Bearer {token}"
```

### 3. Verificar Estrutura do Projeto
```bash
mvn clean package
# Gera JAR em: target/backend_login_page-0.0.1-SNAPSHOT.jar
```

---

## 🚀 Melhorias Futuras (Prioridade)

### 🔴 Críticas
- [ ] Validação de entrada com `@Valid`
- [ ] Separar lógica em service layer

### 🟠 Importantes
- [ ] Adicionar logging com `@Slf4j`
- [ ] Implementar rate limiting
- [ ] Corrigir typos (CostomUserDetaiLsService)

### 🟡 Nice-to-Have
- [ ] Swagger/SpringDoc
- [ ] Testes de integração E2E
- [ ] Migrar para PostgreSQL
- [ ] Implementar 2FA

---

## 📞 Contato & Suporte

Para dúvidas sobre:
- **Exceções**: Ver `infra/exception/`
- **Testes**: Ver `src/test/java/`
- **Melhorias**: Ver `ANALISE_E_MELHORIAS.md`
- **Resumo**: Ver `RESUMO_IMPLEMENTACOES.md`

---

**Última Atualização:** 29 de novembro de 2025  
**Versão:** 0.0.1-SNAPSHOT
