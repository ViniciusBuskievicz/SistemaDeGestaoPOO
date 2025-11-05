package SistemaDeGestao;

import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido. O email deve conter letras antes do @.com .");
        }
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
    }

    private boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Verifica se contém @
        if (!email.contains("@")) {
            return false;
        }

        // Pega a parte antes do @
        String parteDoComeço = email.substring(0, email.indexOf('@'));
        
        // Verifica se está vazio
        if (parteDoComeço.isEmpty()) {
            return false;
        }

        // Verifica se contém pelo menos uma letra
        boolean temLetra = false;
        for (char c : parteDoComeço.toCharArray()) {
            if (Character.isLetter(c)) {
                temLetra = true;
                break;
            }
        }

        // Verifica se tem algo depois do @
        String parteDoFinal = email.substring(email.indexOf('@') + 1);
        if (parteDoFinal.isEmpty() || !parteDoFinal.contains(".")) {
            return false;
        }

        return temLetra;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Cliente: " + nome + " (" + email + ")";
    }
}