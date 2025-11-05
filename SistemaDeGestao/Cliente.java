package SistemaDeGestao;

import java.util.UUID;

// Classe que representa um cliente no sistema
public class Cliente {
    // Atributos básicos de um cliente
    private UUID id;        // Identificador único universal
    private String nome;    // Nome do cliente
    private String email;   // Email do cliente

    // Construtor que valida e inicializa um novo cliente
    public Cliente(String nome, String email) {
        // Validação do nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        // Validação do email usando método específico
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("Email inválido. O email deve conter letras antes do @.com .");
        }
        // Inicialização dos atributos
        this.id = UUID.randomUUID();    // Gera um ID único
        this.nome = nome;
        this.email = email;
    }

    // Método privado para validar o formato do email
    private boolean validarEmail(String email) {
        // Verificações básicas
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Verifica presença do @
        if (!email.contains("@")) {
            return false;
        }

        // Extrai e valida a parte antes do @
        String parteDoComeço = email.substring(0, email.indexOf('@'));
        if (parteDoComeço.isEmpty()) {
            return false;
        }

        // Verifica se existe pelo menos uma letra antes do @
        boolean temLetra = false;
        for (char c : parteDoComeço.toCharArray()) {
            if (Character.isLetter(c)) {
                temLetra = true;
                break;
            }
        }

        // Valida a parte após o @ (deve conter um ponto)
        String parteDoFinal = email.substring(email.indexOf('@') + 1);
        if (parteDoFinal.isEmpty() || !parteDoFinal.contains(".")) {
            return false;
        }

        return temLetra;
    }

    // Métodos de acesso (getters)
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    // Sobrescrita do método toString para melhor representação do objeto
    @Override
    public String toString() {
        return "Cliente: " + nome + " (" + email + ")";
    }
}