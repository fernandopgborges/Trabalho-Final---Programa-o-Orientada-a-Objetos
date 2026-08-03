# Sistema de Locação de Jogos

## Descrição

O presente projeto foi desenvolvido como trabalho final da disciplina de Programação Orientada a Objetos, tendo como objetivo aplicar, de forma prática, os conceitos abordados ao longo da disciplina.

A aplicação consiste em um sistema web para gerenciamento de uma locadora de jogos, permitindo o cadastro, a consulta e o controle de clientes, jogos, formas de pagamento e contratos de locação.

O desenvolvimento foi realizado utilizando a linguagem Java, em conjunto com o framework Spring Boot e a biblioteca Vaadin para construção da interface web.

## Funcionalidades

O sistema disponibiliza as seguintes funcionalidades:

* Cadastro e gerenciamento de clientes;
* Cadastro e gerenciamento de jogos;
* Cadastro e gerenciamento de formas de pagamento;
* Cadastro de contratos de locação;
* Leitura de dados iniciais a partir de arquivos no formato CSV;
* Consultas específicas, incluindo:

  * Identificação do jogo com maior valor diário;
  * Identificação do contrato com maior valor final;
  * Identificação do cliente com maior volume de contratos.

## Tecnologias Utilizadas

* Java
* Spring Boot
* Vaadin
* Maven

## Conceitos Aplicados

Durante o desenvolvimento do projeto, foram aplicados os seguintes conceitos:

* Programação Orientada a Objetos;
* Encapsulamento, herança e polimorfismo;
* Utilização de estruturas de dados, como TreeMap e Queue;
* Manipulação de arquivos no formato CSV;
* Organização modular do sistema.

## Execução do Projeto

Para executar a aplicação, siga os passos descritos abaixo:

1. Realizar o clone do repositório:
   git clone https://github.com/fernandopgborges/Trabalho-Final---Programa-o-Orientada-a-Objetos.git

2. Acessar o diretório do projeto:
   cd Trabalho-Final---Programa-o-Orientada-a-Objetos

3. Executar a aplicação utilizando o Maven:
   mvn spring-boot:run

4. Acessar a aplicação por meio do navegador:
   http://localhost:8080

## Observações

Os dados iniciais do sistema são carregados a partir de arquivos CSV localizados na pasta de recursos do projeto. Esses arquivos são utilizados para inicializar as estruturas de dados em memória no momento da execução.

## Autores

Fernando Pacheco Gutterres Borges | Guilherme Mennet Bueno | Gustavo Gleich Suarez
