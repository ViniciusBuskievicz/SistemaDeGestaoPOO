package SistemaDeGestao;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return quantidade + "x " + produto.getNome() + " = R$" + calcularSubtotal();
    }
}