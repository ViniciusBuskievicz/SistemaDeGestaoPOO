package SistemaDeGestao;

import java.util.*;

public class Sistema {
    // Estruturas de dados para armazenamento em memória
    private static List<Cliente> clientes = new ArrayList<>();     // Lista de clientes cadastrados
    private static List<Produto> produtos = new ArrayList<>();     // Lista de produtos disponíveis
    private static List<Pedido> pedidos = new ArrayList<>();      // Histórico de todos os pedidos
    private static Queue<Pedido> filaPedidos = new LinkedList<>(); // Fila de pedidos para processamento
    private static Scanner scanner = new Scanner(System.in);       // Scanner para entrada de dados
    private static ProcessadorPedidos processador;                 // Processador de pedidos em background
    private static Thread threadProcessador;                       // Thread para processamento assíncrono

    public static void main(String[] args) {
        // Inicialização do sistema de processamento assíncrono de pedidos
        processador = new ProcessadorPedidos(filaPedidos);
        threadProcessador = new Thread(processador);
        threadProcessador.start();

        // Exibe o menu principal em loop
        exibirMenu();
        
        // Finalização segura do processador de pedidos
        processador.stop();
        try {
            threadProcessador.join();
        } catch (InterruptedException e) {
            System.out.println("Erro ao encerrar processador de pedidos");
        }
        scanner.close();
    }

    private static void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n|=====MENU PRINCIPAL======|");
            System.out.println("|1. Cadastrar cliente     |");
            System.out.println("|2. Cadastrar produto     |");
            System.out.println("|3. Criar pedido          |");
            System.out.println("|4. Listar clientes       |");
            System.out.println("|5. Listar produtos       |");
            System.out.println("|6. Listar pedidos        |");
            System.out.println("|0. Sair                  |");
            System.out.print(  "|_________________________|\n");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarProduto();
                case 3 -> criarPedido();
                case 4 -> listarClientes();
                case 5 -> listarProdutos();
                case 6 -> listarPedidos();
                case 0 -> {
                    System.out.println("Encerrando o sistema...");
                    processador.stop();
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // Cadastro de cliente com validação
    private static void cadastrarCliente() {
        // Loop principal para cadastro de cliente
        while (true) {
            // Validação do nome do cliente
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Erro: Nome não pode ser vazio. Por favor, tente novamente.");
                continue;
            }

            // Loop para validação do email
            while (true) {
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                
                try {
                    // Tenta criar e adicionar novo cliente
                    Cliente novoCliente = new Cliente(nome, email);
                    clientes.add(novoCliente);
                    System.out.println("Cliente cadastrado com sucesso!");
                    return;
                } catch (IllegalArgumentException e) {
                    // Tratamento de erro para email inválido
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Por favor, tente inserir o email novamente.");
                }
            }
        }
    }

    // Cadastro de produto com validação
    private static void cadastrarProduto() {
        while (true) {
            // Validação do nome do produto
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Nome não pode ser vazio. Tente novamente.");
                continue;
            }

            // Validação e conversão do preço
            System.out.print("Preço: ");
            String precoStr = scanner.nextLine().trim().replace(',', '.');
            double preco;
            try {
                preco = Double.parseDouble(precoStr);
                if (preco <= 0) {
                    System.out.println("Erro: o preço do produto deve ser um número positivo.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Digite um número válido.");
                continue;
            }

            // Seleção da categoria do produto
            CategoriaProduto categoria = null;
            while (categoria == null) {
                // Menu de seleção de categoria
                System.out.println("Selecione a categoria:");
                System.out.println("1. ALIMENTOS");
                System.out.println("2. ELETRONICOS");
                System.out.println("3. LIVROS");
                System.out.print("Opção (1-3): ");
                
                // Validação da escolha da categoria
                String op = scanner.nextLine().trim();
                try {
                    int escolha = Integer.parseInt(op);
                    if (escolha == 1) categoria = CategoriaProduto.ALIMENTOS;
                    else if (escolha == 2) categoria = CategoriaProduto.ELETRONICOS;
                    else if (escolha == 3) categoria = CategoriaProduto.LIVROS;
                    else System.out.println("Opção inválida. Escolha entre 1 e 3.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite o número da opção.");
                }
            }

            // Criação e adição do novo produto
            produtos.add(new Produto(nome, preco, categoria));
            System.out.println("Produto cadastrado com sucesso: " + nome + " - " + categoria + " - R$" + preco);
            break; // cadastro concluído, sai do loop
        }
    }

    // Criação de pedido com múltiplos itens
    private static void criarPedido() {
        // Verificação de pré-requisitos
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Cadastre pelo menos um cliente e um produto antes de criar pedidos.");
            return;
        }

        // Seleção do cliente
        listarClientes();
        System.out.print("Escolha o índice do cliente: ");
        int idxCliente = scanner.nextInt();
        scanner.nextLine();

        // Validação do índice do cliente
        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        // Criação do pedido e adição de itens
        Pedido pedido = new Pedido(clientes.get(idxCliente));
        String continuar = "s";
        
        // Loop para adicionar múltiplos itens ao pedido
        while (continuar.equalsIgnoreCase("s")) {
            // Seleção e validação dos produtos
            listarProdutos();
            System.out.print("Escolha o índice do produto: ");
            int idxProduto = scanner.nextInt();
            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            scanner.nextLine();

            if (idxProduto < 0 || idxProduto >= produtos.size()) {
                System.out.println("Índice inválido.");
                continue;
            }

            // Adiciona item ao pedido com tratamento de erro
            try {
                pedido.adicionarItem(produtos.get(idxProduto), qtd);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.print("Adicionar mais itens? (s/n): ");
            continuar = scanner.nextLine();
        }

        // Finalização e processamento do pedido
        pedido.setStatus(StatusPedido.FILA);
        pedidos.add(pedido);
        filaPedidos.add(pedido);
        
        // Exibição do resumo do pedido e aguardo do processamento
        System.out.println("\n=== Resumo do Pedido ===");
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Itens do pedido:");
        for (ItemPedido item : pedido.getItens()) {
            System.out.println("- " + item.getProduto().getNome() + 
                             " (Qtd: " + item.getQuantidade() + 
                             ") - R$" + item.calcularSubtotal());
        }
        System.out.println("Total do pedido: R$" + pedido.calcularTotal());
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("Pedido criado e adicionado à fila de processamento.");
        
        // Aguarda o processamento completo do pedido
        try {
            Thread.sleep(2000); // Aguarda 2 segundos para ver as mensagens de processamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Aguarda até o pedido ser finalizado
        while (pedido.getStatus() != StatusPedido.FINALIZADO) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Após todas as mensagens de processamento, pede para pressionar ENTER
        System.out.println("\nPressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }

    // Listagem de clientes
    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(i + " - " + clientes.get(i));
        }
    }

    // Listagem de produtos
    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(i + " - " + produtos.get(i));
        }
    }

    // Listagem de pedidos com status atualizado
    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido criado.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }
}