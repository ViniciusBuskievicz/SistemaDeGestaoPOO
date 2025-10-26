package SistemaDeGestao;

import java.util.*;

public class Sistema {
    // Listas que armazenam os dados em memória
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Queue<Pedido> filaPedidos = new LinkedList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicia a thread que processa pedidos em segundo plano
        new Thread(new ProcessadorPedidos(filaPedidos)).start();

        // Exibe o menu principal em loop
        exibirMenu();
    }

    private static void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Cadastrar produto");
            System.out.println("3. Criar pedido");
            System.out.println("4. Listar clientes");
            System.out.println("5. Listar produtos");
            System.out.println("6. Listar pedidos");
            System.out.println("0. Sair");
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
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // Cadastro de cliente com validação
    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            clientes.add(new Cliente(nome, email));
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Cadastro de produto com validação
    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpa buffer
        System.out.print("Categoria (ALIMENTOS, ELETRONICOS, LIVROS): ");
        String categoriaStr = scanner.nextLine().toUpperCase();

        try {
            CategoriaProduto categoria = CategoriaProduto.valueOf(categoriaStr);
            produtos.add(new Produto(nome, preco, categoria));
            System.out.println("Produto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Criação de pedido com múltiplos itens
    private static void criarPedido() {
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Cadastre pelo menos um cliente e um produto antes de criar pedidos.");
            return;
        }

        listarClientes();
        System.out.print("Escolha o índice do cliente: ");
        int idxCliente = scanner.nextInt();
        scanner.nextLine();

        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Pedido pedido = new Pedido(clientes.get(idxCliente));

        String continuar = "s"; // Inicializa para entrar no loop
        while (continuar.equalsIgnoreCase("s")) {
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

            try {
                pedido.adicionarItem(produtos.get(idxProduto), qtd);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.print("Adicionar mais itens? (s/n): ");
            continuar = scanner.nextLine();
        }

        pedido.setStatus(StatusPedido.FILA);
        pedidos.add(pedido);
        filaPedidos.add(pedido);
        System.out.println("Pedido criado e adicionado à fila de processamento.");
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