package SistemaDeGestao;

import java.util.UUID;

public class Produto {
    private UUID id;
    private String nome;
    private double preco;
    private CategoriaProduto categoria;

    public Produto(String nome, double preco, CategoriaProduto categoria) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo.");
        }
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public CategoriaProduto getCategoria() { return categoria; }

    @Override
    public String toString() {
        return nome + " - R$" + preco + " [" + categoria + "]";
    }
}