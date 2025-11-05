package SistemaDeGestao;

// Classe que representa um item individual em um pedido
public class ItemPedido {
    // Atributos do item
    private Produto produto;    // Produto selecionado
    private int quantidade;     // Quantidade do produto

    // Construtor que valida e inicializa um novo item do pedido
    public ItemPedido(Produto produto, int quantidade) {
        // Validação da quantidade
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Métodos de acesso (getters)
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    // Calcula o valor total do item (preço * quantidade)
    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

    // Sobrescrita do toString para formatação personalizada do item
    @Override
    public String toString() {
        return quantidade + "x " + produto.getNome() + " = R$" + calcularSubtotal();
    }
}