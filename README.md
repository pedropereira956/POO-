

# Projeto POO - SpotifyUM

Simulador do Spotify em Java (Programação Orientada a Objetos).

---

## 🗂️ Estrutura do Repositório

* **`/src/main/java`**: Código-fonte da aplicação (contém as lógicas de Músicas, Playlists, Planos de Subscrição e Utilizadores).
* **`/src/test/java`**: Testes unitários para validar o funcionamento das classes.
* **`pom.xml`**: Ficheiro de configuração do Maven (gestão de dependências e compilação).
* **`Classe UML.png`** e **`relatorio.pdf`**: Documentação do projeto e diagrama estrutural de classes.

---

## 🚀 Como Executar o Programa

Este é um projeto Java gerido com o **Maven**. Certifica-te de que tens o Java Development Kit (JDK) e o Maven instalados no teu computador. 

Abre o teu terminal na pasta raiz do projeto (onde se encontra o `pom.xml`) e segue os passos:

### 1️⃣ Compilar o código
Antes de executar, compila o projeto com o Maven:
```bash
mvn compile

2️⃣ Iniciar a aplicação

Após a compilação, executa o programa principal através do comando:
Bash

mvn exec:java -Dexec.mainClass="org.example.Main"
