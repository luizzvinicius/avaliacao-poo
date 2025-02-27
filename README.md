# Avaliação POO
Projeto apresentado como requisito para dispensa na matéria

Critérios de Avaliação
- Utilizar classes abstratas, classes não abstratas, e interfaces;
- Uso adequado da OO: classes e encapsulamento;
- Uso de construtores;
- Aplicação do conceito de herança;
- Uso de Polimorfismo;
- Uso de Collections;
- Uso de Testes Unitários automatizados para cada regra de negócio (BO) e acesso ao BD (DAO);
- Utilizar ao menos 2 padrões de projeto e explicar onde está sendo aplicado;
- Uso de banco de dados (insert, update, delete, select);
- Estruturação do projeto em camadas (View, BO, VO, DAO);
- Uso de exceções (deve-se cria exceções próprias e tratá-las corretamente).

## Uso de Polimorfismo
    Métodos nos DAOs
    Classe Utils
## Uso dos Design Patterns
    1. Factory: classe dedicada para a criação da conexão com o banco de dados
    2. Singleton: estratégia para a criação de única conexão com banco de dados
    3. Strategy: definição de estratégia para cálculo do preço do produto na hora do pagamento

## Execução do projeto
    1. Crie o banco (PostgreSQL) localmente
    2. Crie as variáveis de ambiente ou sobrescreva-as no arquivo infra/ConncectionFactory
    3. Execute o projeto

# Modelo Relacional
![Image](https://github.com/user-attachments/assets/ca3e102f-3872-42b3-984a-6987b95bbfe1)
