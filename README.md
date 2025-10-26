# Sistema de Gestão de Pedidos

Um sistema de gestão de pedidos que permite cadastrar clientes, produtos e processar pedidos de forma concorrente.

## Equipe
- [Aline Avila Brunetti](https://github.com/AlineBrunetti)
- [Nicolas Miguel Uczak](https://github.com/Niccolaszak)
- [Vinicius Gabriel Buskievicz](https://github.com/ViniciusBuskievicz)

## Como Executar

1. Certifique-se de ter o JDK 17+ instalado
2. Clone o repositório
3. Na pasta do projeto, compile os arquivos:
```bash
javac SistemaDeGestao/*.java
```
4. Execute o sistema:
```bash
java SistemaDeGestao.Sistema
```

## Fundamentos de POO Aplicados

### Classes e Encapsulamento
- `Cliente`: Encapsula dados do cliente (nome, email)
- `Produto`: Encapsula dados do produto (nome, preço, categoria)
- `Pedido`: Gerencia itens e status do pedido
- `ItemPedido`: Representa item individual no pedido

### Herança
- `ProcessadorPedidos` implementa `Runnable` para processamento assíncrono

### Polimorfismo
- Sobrescrita do método `toString()` em várias classes para formatação personalizada
- Uso de interfaces da Collection Framework (List, Queue)

### Enums
- `StatusPedido`: Define estados possíveis do pedido
- `CategoriaProduto`: Define categorias disponíveis para produtos

## Princípios SOLID

### Single Responsibility Principle
- Cada classe tem uma única responsabilidade
- `ProcessadorPedidos`: Apenas processa pedidos
- `ItemPedido`: Gerencia apenas dados de um item

### Open/Closed Principle
- Sistema extensível para novos tipos de produtos/categorias
- Processamento de pedidos independente da implementação

### Interface Segregation
- Uso de interfaces coesas (Runnable para processamento)

### Dependency Inversion
- Sistema trabalha com abstrações (List, Queue)
- Baixo acoplamento entre componentes

## Object Calisthenics

1. Um nível de indentação por método
2. Não use else
3. Encapsule tipos primitivos (uso de classes para Cliente, Produto, etc)
4. Coleções encapsuladas
5. Métodos pequenos e focados

## Detalhes Técnicos

### Concorrência
- Processamento assíncrono de pedidos usando Thread
- Queue thread-safe para fila de pedidos
- Status do pedido atualizado em tempo real

### Validações
- Validação de dados em construtores
- Tratamento de exceções para entradas inválidas
- Verificações de valores negativos/nulos

### Gerenciamento de Estado
- Enums para controle de estado do pedido
- Status transitam de forma controlada
- Feedback em tempo real do processamento

### Identificação Única
- Uso de UUID para identificação única de entidades
- Permite escalabilidade e unicidade garantida

## Estrutura do Projeto
```
SistemaDeGestao/
├── Sistema.java         # Classe principal
├── Cliente.java         # Entidade Cliente
├── Produto.java         # Entidade Produto
├── Pedido.java         # Entidade Pedido
├── ItemPedido.java     # Componente de Pedido
├── ProcessadorPedidos.java  # Processamento assíncrono
├── StatusPedido.java   # Estados do pedido
└── CategoriaProduto.java    # Categorias disponíveis
```
