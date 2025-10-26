package SistemaDeGestao;

import java.util.*;

public class Pedido {
    private UUID id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedido status;

    public Pedido(Cliente cliente) {
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new ItemPedido(produto, quantidade));
    }

    public double calcularTotal() {
        return itens.stream()
                    .mapToDouble(ItemPedido::calcularSubtotal)
                    .sum();
    }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }

    @Override
    public String toString() {
        return "Pedido de " + cliente.getNome() + " - Status: " + status + " - Total: R$" + calcularTotal();
    }
}