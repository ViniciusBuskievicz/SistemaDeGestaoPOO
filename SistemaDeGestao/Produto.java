package SistemaDeGestao;

import java.util.UUID;

// Classe que representa um produto no sistema
public class Produto {
    // Atributos básicos do produto
    private UUID id;                    // Identificador único do produto
    private String nome;                // Nome do produto
    private double preco;               // Preço do produto
    private CategoriaProduto categoria; // Categoria do produto

    // Construtor que valida e inicializa um novo produto
    public Produto(String nome, double preco, CategoriaProduto categoria) {
        // Validação do preço
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo.");
        }
        // Inicialização dos atributos
        this.id = UUID.randomUUID();    // Gera ID único
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Métodos de acesso (getters)
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public CategoriaProduto getCategoria() { return categoria; }

    // Sobrescrita do toString para formatação personalizada do produto
    @Override
    public String toString() {
        return nome + " - R$" + preco + " [" + categoria + "]";
    }
}