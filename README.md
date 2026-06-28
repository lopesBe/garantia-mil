# 🛡️ App de Garantias — MILL INDÚSTRIAS

Sistema web interno para gestão centralizada de **Reclamações de Clientes**, **Solicitações de Garantia** e **Análises de Qualidade**.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Executar
```bash
cd c:\faculdade\milind
mvn spring-boot:run
```

A aplicação iniciará em: **http://localhost:8080**

### Credenciais de Acesso

| Usuário      | Senha      | Perfil       |
|-------------|------------|--------------|
| `admin`     | `admin123` | ADMIN        |
| `qualidade` | `admin123` | QUALIDADE    |

---

## 🗂️ Módulos

| Módulo         | URL              | Prefixo    |
|---------------|------------------|------------|
| Login         | `/login`         | —          |
| Menu          | `/menu`          | —          |
| Reclamações   | `/reclamacoes`   | `REC-XXXX` |
| Garantias     | `/garantia`      | `GAR-XXXX` |
| Qualidade     | `/qualidade`     | `QLD-XXXX` |

---

## 🔧 Stack Tecnológica

- **Backend**: Java 17 + Spring Boot 3.2
- **Segurança**: Spring Security + BCrypt
- **ORM**: Spring Data JPA + Hibernate
- **Banco**: H2 in-memory (H2 Console em `/h2-console`)
- **Frontend**: Thymeleaf + CSS3 + JavaScript vanilla
- **Build**: Maven

---

## 🎨 Identidade Visual MILL

| Token                | Valor     |
|---------------------|-----------|
| Cor primária (amarelo) | `#F5C400` |
| Fundo principal     | `#3A3A3A` |
| Cards / Formulários | `#4A4A4A` |
| Fundo escuro (header) | `#2A2A2A` |
| Texto principal     | `#FFFFFF` |

---

## 📁 Estrutura do Projeto

```
src/main/java/com/mill/garantias/
├── MillGarantiasApplication.java
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ReclamacaoController.java
│   ├── GarantiaController.java
│   └── QualidadeController.java
├── model/
│   ├── Usuario.java
│   ├── Reclamacao.java
│   ├── Garantia.java
│   ├── GarantiaItem.java
│   └── AnaliseQualidade.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── ReclamacaoRepository.java
│   ├── GarantiaRepository.java
│   └── AnaliseQualidadeRepository.java
└── service/
    ├── UsuarioDetailsService.java
    ├── ReclamacaoService.java
    ├── GarantiaService.java
    └── AnaliseQualidadeService.java

src/main/resources/
├── application.properties
├── data.sql
├── static/css/style.css
└── templates/
    ├── login.html
    ├── menu.html
    ├── fragments/layout.html
    ├── reclamacoes/
    │   ├── lista.html
    │   └── formulario.html
    ├── garantia/
    │   ├── lista.html
    │   └── formulario.html
    └── qualidade/
        ├── lista.html
        └── formulario.html
```

---

## ✨ Funcionalidades

- ✅ Login/Logout com Spring Security + BCrypt
- ✅ Numeração automática: `REC-0001`, `GAR-0001`, `QLD-0001`
- ✅ Paginação (10 itens/página) em todas as listagens
- ✅ Filtros por GET em todas as listagens
- ✅ Seleção de linha (highlight amarelo) + botões Editar/Excluir contextuais
- ✅ Modal de confirmação antes de excluir
- ✅ Badges de status coloridos (Aprovada=verde, Reprovada=vermelho, Pendente=amarelo, Em Análise=cinza)
- ✅ Itens da garantia dinâmicos (adicionar/remover linhas via JavaScript)
- ✅ Vínculo entre módulos (Garantia → Reclamação, Análise → Reclamação + Garantia)
- ✅ Flash messages de sucesso/erro
- ✅ Dados de exemplo pré-carregados
