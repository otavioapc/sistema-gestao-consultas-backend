# VestaPlan API 🦷🛡️

API RESTful robusta e segura para o gerenciamento clínico e administrativo de clínicas odontológicas. Desenvolvida com **Java 21** e **Spring Boot**, a solução conta com autenticação baseada em tokens **JWT**, criptografia de senhas e regras rígidas de consistência de dados no banco de dados **PostgreSQL**.

---

## 🛠️ Tecnologias e Ferramentas
* **Java 21** & **Spring Boot 4.x**
* **Spring Security** & **JWT (Java JSON Web Token)**
* **Spring Data JPA** & **Hibernate**
* **PostgreSQL** (com suporte à busca fonética `unaccent`)

---

## 🗄️ Arquitetura do Sistema
O projeto segue o padrão de arquitetura em camadas para garantir a separação de responsabilidades, manutenibilidade e escalabilidade:
1. **Controllers**: Exposição dos endpoints REST e validação inicial de payloads (`@Valid`).
2. **Services**: Centralização das regras de negócio (ex: validação de choque de horários).
3. **Repositories**: Comunicação com o banco através do Spring Data JPA e queries nativas otimizadas.
4. **DTOs (Records)**: Padrão imutável para tráfego seguro de dados entre cliente e servidor.

---

## 🔐 Segurança e Autenticação
* **Criptografia**: Senhas encriptadas via `BCryptPasswordEncoder` antes do armazenamento no banco.
* **Sessão Stateless**: Filtro customizado (`SecurityFilter`) intercepta cada requisição, extrai o Token JWT do cabeçalho `Authorization: Bearer <token>`, valida a assinatura e autentica o usuário no contexto global do Spring Security.

---

## 📌 Documentação dos Endpoints (Rotas)

### 🔑 Autenticação
* `POST /login` -> Realiza o login do usuário. Retorna o token JWT de acesso.

### 👥 Usuários
* `GET /usuarios` -> Lista todos os usuários cadastrados.
* `GET /usuarios/{id}` -> Busca um usuário pelo ID único.
* `GET /usuarios/nome?nome=...` -> Busca fonética de usuários por aproximação de nome.
* `POST /usuarios` -> Cadastra um novo operador no sistema (Senha salva com Hash criptográfico).
* `PUT /usuarios/{id}` -> Atualiza os dados cadastrais e credenciais do usuário.
* `DELETE /usuarios/{id}` -> Remove o registro de acesso do usuário.

### 🏷️ Especialidades
* `GET /especialidades` -> Lista todas as especialidades odontológicas cadastradas.
* `POST /especialidades` -> Adiciona uma nova especialidade técnica.

### 🦷 Dentistas
* `GET /dentistas` -> Lista todos os dentistas.
* `GET /dentistas/nome?nome=...` -> Busca aproximada de profissionais.
* `POST /dentistas` -> Cadastra um dentista associando múltiplas especialidades (`@ManyToMany`).
* `PUT /dentistas/{id}` -> Atualiza dados clínicos do dentista.
* `DELETE /dentistas/{id}` -> Realiza a deleção do registro.

### 👤 Pacientes
* `GET /pacientes` -> Lista todos os pacientes da clínica.
* `GET /pacientes/nome?nome=...` -> Busca por nome do paciente.
* `POST /pacientes` -> Cadastra um novo paciente (Dados higienizados sem máscaras).
* `PUT /pacientes/{id}` -> Atualiza os registros do paciente.
* `DELETE /pacientes/{id}` -> Remove o paciente do sistema.

### 🗓️ Consultas (Agendamentos)
* `GET /consultas` -> Recupera o painel de consultas ativas. Administradores visualizam tudo; profissionais veem suas próprias agendas.
* `POST /consultas` -> Realiza um agendamento. **Regra de Negócio:** O sistema impede automaticamente marcações com choque de horários para o mesmo dentista e injeta o usuário do Token como auditor da ação.
* `PUT /consultas/{id}` -> Reagenda ou atualiza os parâmetros da consulta.
* `PUT /consultas/{id}/cancelar` -> Cancela um agendamento registrando a justificativa técnica.