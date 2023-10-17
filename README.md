# Rede Social Esportiva - Java
Este é um trabalho desenvolvido para a matéria de Banco de Dados da FAESA campus Vitória, 2023/2.
<br>
Este representa uma rede social esportiva. Consiste uma aplicação Java que realiza operações em SQL em um banco de dados Oracle que possui as tabelas ESPORTE e EVENTO (eventos esportivos marcados pelos usuários).

## Integrantes
- Bruno Coutinho Remeikis
- Isabelly Lopes dos Santos
- João Henrique Schultz
- Nicolas Lima
- Pedro Cassemiro

## Tecnologias utilizadas
- <b>Linguagem:</b> Java 17
   - Maven 3.9.3
      - OJDBC 11: Driver para conexão com bancos Oracle
      - Lombok: Torna o Java menos verboso
- <b>IDE:</b> Apache Netbeans IDE 19
- <b>Banco de Dados:</b> Oracle

## Como rodar o projeto
1. Clone este repositório em seu computador.

   - Com o GIT instalado em sua máquina, rode o seguinte comando em um terminal apontando para o local que desejar de sua máquina:
   ```cmd
   git clone https://github.com/bruno-remeikis/rede-social-esportiva.git
   ```

2. Tenha um banco de dados Oracle criado. Rode o script [sql/sql_bancodedados.sql](sql) em seu banco.

3. Construa, compile e execute o projeto.

   - <b>Opção 1:</b> Com o Maven instalado e configurado em sua máquina, abra o terminal na [raiz do projeto java](rede-social-esportiva-java) e execute os comandos:
   ```cmd
   mvn clean package
   ```
   ```cmd
   mvn exec:java
   ```
   
   - <b>Opção 2:</b> Abra o [projeto java](rede-social-esportiva-java) em uma IDE (de preferência no NetBeans 19 ou versões próximas), construa e execute o projeto.

   - <b>Opção 3:</b> Em uma IDE de sua preferência, crie um novo projeto Java com Maven. Feito isso, copie o(s) diretórios(s) que contém os códigos-fonte para dentro de seu novo projeto. Lembre-se de de configurar o arquivo `pom.xml` do seu novo projeto da mesma forma que o [pom.xml](rede-social-esportiva-java/pom.xml) deste projeto está configurado.

<a id="ancora-config-conexao-bd"></a>

### Configurar Conexão com Banco de Dados
Para configurar seus dados de conexão, basta alterar as Strings definidas no início da classe [OracleConnector.java](rede-social-esportiva-java/src/main/java/com/faesa/app/connection/OracleConnector.java).

```java
...

public class OracleConnector
{
    private static final String
        USER = "system",
        PASSWORD = "oracle",
        HOST = "localhost",
        PORT = "1521",
        SID = "XE",
        URL = "jdbc:oracle:thin:@" + HOST + ':' + PORT + ':' + SID;
        
...
```

## Estrutura de diretórios
Os principais diretórios do repositório estão dispostos da seguinte forma:

- [diagrams](diagrams): Contém os diagramas Relacional (lógico) e de Entidade e Relacionamento do sistema.

- [sql](sql): Contém os scripts para criação das tabelas e inserção de dados fictícios para testes do sistema.

- [videos](videos): Contém o(s) vídeo(s) de demonstração da aplicação.

   - Certifique-se de que o usuário do banco possui todos os privilégios antes de executar os scripts de criação. Caso ocorra erro, execute o comando `GRANT ALL PRIVILEGES TO <NOME_DO_BANCO>;` com o superusuário em seu ambiende de banco de dados, substituindo o trecho `<NOME_DO_BANCO>` pelo nome do seu banco (provavelmente `XE`).

- [rede-social-esportiva-java](rede-social-esportiva-java): Este diretório contem à aplicação Java.
   
   - [src/main/java/com/faesa/app](rede-social-esportiva-java/src/main/java/com/faesa/app)
      
      - [connection](rede-social-esportiva-java/src/main/java/com/faesa/app/connection): Contém a(s) classe(s) de configuração da conexão com o banco de dados. ([Clique aqui para ver como configurar sua conexão](#ancora-config-conexao-bd)).

      - [controller](rede-social-esportiva-java/src/main/java/com/faesa/app/controller): Contém as classes controladoras das regras de negócio. Localiza-se, idealmente, em uma camada entre a [principal](rede-social-esportiva-java/src/main/java/com/faesa/app/principal) e a [dao](rede-social-esportiva-java/src/main/java/com/faesa/app/dao), realizando a comunicação entre interface do usuário e a interface de comunicação com o banco de dados.

      - [dao](rede-social-esportiva-java/src/main/java/com/faesa/app/dao): Contém as classes responsáveis pela comunicação com o banco de dados.

         - [DAO.java](rede-social-esportiva-java/src/main/java/com/faesa/app/dao/DAO.java): Classe abstrata que contem apenas recursos que facilitam o desenvolvimento das classes outras classes DAO.

         - &#60;Entidade&#62;DAO: Interfaces que contém os cabeçalhos dos métodos das classes DAO não abstratas. <b>Exemplo:</b> [EsporteDAO.java](rede-social-esportiva-java/src/main/java/com/faesa/app/dao/EsporteDAO.java).

         - &#60;Entidade&#62;DAOOracle: Classes que extendem de [DAO](rede-social-esportiva-java/src/main/java/com/faesa/app/dao/DAO.java) e implementam &#60;Entidade&#62;DAO, definindo o corpo dos métodos de sua interface. Estas são as que, de fato, possuem os códigos SQL da aplicação, bem como o tratamento de entrada e saída de dados do banco de dados. <b>Exemplo:</b> [EsporteDAOOracle.java](rede-social-esportiva-java/src/main/java/com/faesa/app/dao/EsporteDAOOracle.java).

      - [model](rede-social-esportiva-java/src/main/java/com/faesa/app/model): Nesse diretório encontram-ser as classes das entidades descritas nos [diagramas](diagrams).

      - [principal](rede-social-esportiva-java/src/main/java/com/faesa/app/principal): Classes que exibem a interface do usuário, permitindo que este se comunique com a aplicação.
      
         - [App.java](rede-social-esportiva-java/src/main/java/com/faesa/app/principal/App.java): Responsável por iniciar a execução do programa através do método `main`. Define os principais menús e o fluxo principal a ser exibido. É uma classe abstrata e possui métodos abstratos. É extendida por "sub-Apps" responsáveis pelas operações com entidades específicas.

         - &#60;Entidade&#62;App.java: "Sub-aplicação" que define as operações a serem realizadas com a &#60;Entidade&#62;. <b>Exemplo: </b> [AppEsporte.java](rede-social-esportiva-java/src/main/java/com/faesa/app/principal/AppEsporte.java).

      - [util](rede-social-esportiva-java/src/main/java/com/faesa/app/util): Contém classes de facilitam e automatizam certas operações como: entrada de dados pelo usuário, manuseio de Strings e exibição de tabelas.
   
   - [pom.xml](rede-social-esportiva-java/pom.xml): Arquivo de configuração do Maven, responsavel pelo gerenciamento do projeto e de suas dependências.
