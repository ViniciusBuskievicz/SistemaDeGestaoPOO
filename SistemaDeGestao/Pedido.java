package SistemaDeGestao;

import java.util.*;

// Classe que representa um pedido no sistema
public class Pedido {
    // Atributos do pedido
    private UUID id;                    // Identificador único do pedido
    private Cliente cliente;            // Cliente que fez o pedido
    private List<ItemPedido> itens;     // Lista de itens do pedido
    private StatusPedido status;        // Status atual do pedido

    // Construtor que inicializa um novo pedido
    public Pedido(Cliente cliente) {
        this.id = UUID.randomUUID();            // Gera ID único
        this.cliente = cliente;                 // Define o cliente
        this.itens = new ArrayList<>();         // Inicializa lista vazia de itens
        this.status = StatusPedido.ABERTO;      // Define status inicial como ABERTO
    }

    // Método para adicionar um novo item ao pedido
    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new ItemPedido(produto, quantidade));
    }

    // Calcula o valor total do pedido somando os subtotais de cada item
    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(ItemPedido::calcularSubtotal)
                    .sum();
    }

    // Métodos de acesso (getters e setters)
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }

    // Sobrescrita do toString para exibição formatada do pedido
    @Override
    public String toString() {
        return "Pedido de " + cliente.getNome() + " - Status: " + status + " - Total: R$" + calcularTotal();
    }
}