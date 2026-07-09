GambittVF-AntiCheat 
Sistema Anticheat desenvolvido em Java utilizando MVC, DAO, Swing e MySQL.
Este trabalho esta em constante evolução

Sistema desenvolvido em Java utilizando arquitetura MVC, persistência de dados com DAO e banco de dados MySQL.

## Tecnologias

- Java 26
- Swing
- MySQL
- JDBC
- IntelliJ IDEA

## Arquitetura

O projeto segue o padrão MVC.

```
Model
Controller
DAO
View
```

## Funcionalidades

- Cadastro de jogadores
- Cadastro de processos
- Detecção de processos suspeitos
- Banimento automático
- Registro de alertas
- Consulta de jogadores
- Consulta de processos
- Consulta de alertas
- Consulta de jogadores banidos
- Desbanimento de jogadores

## Banco de Dados

Banco utilizado:

```
gambittvf
```

Execute o arquivo:

```
sql/banco_gambittvf.sql
```

## Como executar

1. Clone o projeto

```
git clone https://github.com/SEU_USUARIO/GambittVF-AntiCheat.git
```

2. Abra no IntelliJ

3. Adicione o MySQL Connector

4. Configure a senha em

```
src/dao/Conexao.java
```

5. Execute

```
Main.java
```

## Estrutura

```
src
 ├── controller
 ├── dao
 ├── model
 ├── util
 └── view
```

## Autor

Vitor Faccin
