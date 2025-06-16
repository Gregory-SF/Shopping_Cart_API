# Shopping_Cart_API
Este repositório contém uma API para um sistema de carrinho de compras, feita em Java.

---

## Pré-requisitos

- **Java 23**  
  Baixe o Java JDK 23 em: https://www.oracle.com/java/technologies/downloads/
  <br>Após instalar, adicione o Java ao `PATH` do sistema. Para verificar a instalação, use no terminal:
  ```
  java -version
  ```

- **Eclipse IDE**  
  Baixe o Eclipse em: https://www.eclipse.org/downloads/
  <br>Recomendo a versão "Eclipse IDE for Java Developers".  
  Após baixar, descompacte e execute o Eclipse.

- **Maven**  
  Baixe o Maven em: https://maven.apache.org/download.cgi  
  Extraia e configure a variável de ambiente `MAVEN_HOME` apontando para o diretório do Maven.
  Adicione também o Maven ao `PATH`.
  Para testar, execute:
  ```
  mvn -version
  ```

- **MySQL**  
  Baixe o MySQL Community Server: https://dev.mysql.com/downloads/mysql/  
  Durante a instalação, defina uma senha para o usuário `root`.  
  Anote essa senha, pois será usada no projeto.

- **XAMPP (para PHPMyAdmin)**  
  Baixe o XAMPP em: https://www.apachefriends.org/pt_br/download.html  
  Instale e abra o XAMPP Control Panel.  
  Inicie os módulos **Apache** e **MySQL**.  
  Acesse o PHPMyAdmin via: http://localhost/phpmyadmin  
  Crie um banco de dados, por exemplo: `shopping_cart_db`.

---

## Configuração do Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/Gregory-SF/Shopping_Cart_API.git
cd Shopping_Cart_API
```

### 2. Importar no Eclipse

1. Abra o Eclipse.
2. Vá em `File > Import... > Existing Maven Projects`.
3. Selecione a pasta do projeto clonado.
4. Finalize e aguarde o Eclipse baixar as dependências.

### 3. Configurar acesso ao banco de dados

No arquivo `src/main/resources/META-INF/persistence.xml`, configure as informações do MySQL, exemplo:
```
<persistence-unit name="seu nome do banco" transaction-type="RESOURCE_LOCAL">
<property name="javax.persistence.jdbc.user" value="seu user"/>
<property name="javax.persistence.jdbc.password" value="sua senha"/>
```
Troque `SUA_SENHA` pela senha definida na instalação do MySQL.

**Obs:** Certifique-se de que o banco está criado no PHPMyAdmin.

---

## Configuração do PHPMyAdmin via XAMPP

1. Abra o XAMPP Control Panel.
2. Inicie **Apache** e **MySQL**.
3. Abra o navegador e acesse: http://localhost/phpmyadmin
4. Crie o banco de dados com o nome desejado (ex: `shopping_cart_db`).
---
