# YardFlow - Gestão Inteligente de Pátio de Motos 🏍️
                                          >>> ORGANIZE | LOCALIZE | FLUA <<<

O YardFlow é um sistema voltado para o controle de entrada, saída e localização de motos em pátios, ideal para empresas privadas que realizam manutenção ou armazenam motocicletas.



## 📌 Índice
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Execução](#-execução)
- [Documantação da API](#-documentação-da-api)
- [Estrutura](#-estrutura)
- [Status da Aplicação](#-status-da-aplicação)
- [Autores](#-autores)
  

## 🚀 Funcionalidades
- **Gerenciamento**:
  - Fazer registros de entrada e de saida das Motos no Pátio
  - Consultas do status da Moto
- **Localização das Motos**:
  - Consultar a localização da Moto


## 💻 Tecnologias
 - Java (v.17)
 - Maven (v.17)
 - SpringBoot (v. 3.4.4)
 - Oracle SQL Developer (v.12)
 - Idea Intellij IDEA
  

## 📋 Pré-requisitos
- IDEA
  - Intellij, ou
  - Eclipse, ou outra da preferência
- JDK 22
- Maven 


## 🔧 Instalação
 - git clone https://github.com/KarenMarquesS/YardFlow.git
 - cd yardflow
 - mvn clean install 


## 🏃 Execução
 - mvn spring-boot:run


## 📘 Documentação da API
A aplicação conta com uma interface interativa gerada pelo Swagger, permitindo testar os endpoints diretamente pelo navegador.
  - Acesse: `http://localhost:8080/swagger-ui.html`


## 🗂 Estrutura
```
src
└── main
├── java
│ └── org.example.yardflow
│ ├── configuration
| | ├──MapperConfig
│ ├── control
| | ├──ClienteController
│ │ ├── MotoController
| | ├── PatioController
| | ├── Registro_in_outController
│ │ └── VagasControler
│ ├── dto
│ │ ├── ClienteDTO
| | ├── MotoDTO
| | ├── PatioDTO
| | ├── Registro_in_outDTO
│ │ └── VagaDTO
| | |__ PatioDTO
│ ├── exception
| | ├──ExceptionGlobal
│ ├── model
│ │ ├── Cliente
│ │ ├── ModeloEnum
│ │ ├── Moto
│ │ ├── Patio
│ │ ├── PlanoEnum
│ │ ├── Registro_check_in_Out
│ │ ├── SetorEnum
│ │ └── Vaga
│ ├── projection
| | ├──PermanenciaPorSetor
│ ├── repository
│ │ ├── ClienteRepositorio
│ │ ├── MotoRepositorio
│ │ ├── PatioRepositorio
│ │ ├── Registro_check_in_OutRepositorio
│ │ └── VagasRepositorio
│ ├── service
│ │ ├── ClienteCachingService
│ │ ├── MotoCachingService
│ │ ├── PatioCachingService
│ │ ├── Registro_check_in_OutCachingService
│ │ └── VagasCachingService
│ ├── swagger
│ │ ├── SwaggerConfig
│ └── YardFlowApplication
└── resources
├── application.properties
└── import.sql
```


## 🚧 Status da Aplicação 
 - Aplicação em Desenvolvimento
   - Cronograma de exceução
     - 30% finalizado até 23/05/2025 (1° e 2° sprint)
     - 30% finalizado em  28/09/2025 (3° sprint)
     - 40% finalizado em  09/11/2025 (4° sprint)     


## 👥 Autores
    Nome	                    RM          GitHub
    Fernanda Budniak de Seda  558274      https://github.com/Febudniak
    Lucas Lerri de Almeida    554635      https://github.com/lerri05
    Karen Marques dos Santos  554556      https://github.com/KarenMarquesS

