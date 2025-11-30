# Contribuindo para Backend Login Page 🤝

Muito obrigado por se interessar em contribuir com o projeto! Este documento fornece diretrizes e instruções para contribuir.

## 📋 Código de Conduta

Todos os contribuidores devem seguir nosso código de conduta:

- Ser respeitoso com todos
- Fornecer feedback construtivo
- Focar no que é melhor para a comunidade
- Respeitar opiniões diferentes

## 🚀 Como Começar

### 1. Fork o Repositório
```bash
# Clique em "Fork" no GitHub
git clone https://github.com/seu-usuario/backend_login_page.git
cd backend_login_page
```

### 2. Criar Branch de Feature
```bash
# Sempre criar branch a partir de develop
git checkout develop
git pull origin develop
git checkout -b feature/sua-feature-descritiva
```

### 3. Fazer Mudanças
```bash
# Editar arquivos, adicionar funcionalidades, etc.
# Seguir padrões de código abaixo
```

### 4. Testes
```bash
# Sempre adicionar testes para novas funcionalidades
mvn test

# Verificar cobertura
mvn clean test jacoco:report
```

### 5. Commits Semânticos
```bash
# Seguir Conventional Commits
git add .
git commit -m "tipo(escopo): descrição concisa

Descrição detalhada se necessário.

Related to #123"
```

## 📝 Tipos de Commits

```
feat:     Nova funcionalidade
fix:      Correção de bug
test:     Adicionar/modificar testes
docs:     Alterações na documentação
style:    Formatação, sem mudança de lógica
refactor: Refatoração de código
perf:     Melhorias de performance
chore:    Atualizações de build, dependencies
ci:       Mudanças em CI/CD
```

## 📚 Padrões de Código

### Java
```java
// ✅ Bom
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    
    public User findById(String id) {
        log.info("Finding user: {}", id);
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

// ❌ Evitar
public class UserService {
    @Autowired
    private UserRepository repository;
    
    public User findById(String id) {
        // sem logging
        // sem tratamento de erro adequado
        return repository.findById(id).get();
    }
}
```

### Testes
```java
// ✅ Bom - Arrange-Act-Assert
@Test
@DisplayName("Should find user by email")
void testFindByEmail() {
    // Arrange
    User user = new User();
    user.setEmail("test@example.com");
    userRepository.save(user);
    
    // Act
    Optional<User> result = userRepository.findByEmail("test@example.com");
    
    // Assert
    assertTrue(result.isPresent());
    assertEquals("test@example.com", result.get().getEmail());
}

// ❌ Evitar
@Test
void test() {
    // Sem displayname
    User u = new User();
    u.setEmail("test@example.com");
    // sem setup claro
    assertTrue(repository.findByEmail("test@example.com").isPresent());
}
```

### Nomes de Classes
```java
// ✅ Bom
public class UserNotFoundException extends RuntimeException {}
public class UserService {}
public class UserController {}

// ❌ Evitar
public class UnknownUserException {}
public class Service {}
public class UtilClass {}
```

## 🧪 Checklist de Contribuição

Antes de fazer o Pull Request, verifique:

- [ ] Código segue padrões do projeto
- [ ] Testes adicionados para novas funcionalidades
- [ ] Todos os testes passando: `mvn test`
- [ ] Sem warnings do compilador
- [ ] Documentação atualizada se necessário
- [ ] Commits semânticos com mensagens claras
- [ ] Branch está atualizado com `develop`
- [ ] Sem arquivos desnecessários commitados

## 📤 Submeter Pull Request

### 1. Push para seu Fork
```bash
git push origin feature/sua-feature-descritiva
```

### 2. Abrir Pull Request
```
Title: feat: adicionar autenticação OAuth2

Description:
- Implementa suporte a OAuth2
- Adiciona testes para validação
- Atualiza documentação

Related to #456
```

### 3. Checklist do PR
```markdown
## Descrição
Breve descrição do que foi feito

## Tipo de Mudança
- [ ] Nova funcionalidade
- [ ] Correção de bug
- [ ] Mudança que quebra compatibilidade

## Testes
- [ ] Testes adicionados
- [ ] Testes passando
- [ ] Cobertura de código > 80%

## Documentação
- [ ] README atualizado
- [ ] Documentação de API atualizada
- [ ] Comentários adicionados para código complexo

## Checklist
- [ ] Meu código segue o style guide do projeto
- [ ] Fiz self-review do meu código
- [ ] Comentários adicionados onde apropriado
- [ ] Documentação atualizada
- [ ] Sem warnings do compilador
- [ ] Testes adicionados que provam o funcionamento
- [ ] Testes novos passam localmente
- [ ] Qualquer mudança que dependente foi atualizada
```

## 🐛 Reportar Bugs

### Use Template de Issue
```markdown
## Descrição
Descrição clara do bug

## Reproduzir
1. Passo 1
2. Passo 2
3. Comportamento inesperado

## Comportamento Esperado
O que deveria acontecer

## Informações do Sistema
- OS: Windows 10
- Java: 21
- Maven: 3.9.6
- Spring Boot: 3.5.7

## Logs/Screenshots
Adicione logs e screenshots relevantes
```

## 💡 Sugerir Melhorias

### Use Template de Discussion
```markdown
## Resumo
Uma descrição clara e concisa da melhoria sugerida

## Motivação
Por que seria útil

## Casos de Uso
Exemplos de como seria utilizado

## Alternativas Consideradas
Outras soluções pensadas
```

## 📖 Recursos Úteis

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Keep a Changelog](https://keepachangelog.com/)
- [Semantic Versioning](https://semver.org/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)

## ❓ Dúvidas?

- Abra uma [Discussion](https://github.com/seu-usuario/backend_login_page/discussions)
- Verifique [Issues Abertas](https://github.com/seu-usuario/backend_login_page/issues)
- Consulte a [Documentação](./README.md)

## 🎉 Bem-vindo como Contribuidor!

Obrigado por contribuir com o projeto! Sua participação é fundamental para melhorar a aplicação.

---

**Últimas atualizações:** 29 de novembro de 2025
