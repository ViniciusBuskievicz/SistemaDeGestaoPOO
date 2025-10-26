package SistemaDeGestao;

import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Cliente: " + nome + " (" + email + ")";
    }
}