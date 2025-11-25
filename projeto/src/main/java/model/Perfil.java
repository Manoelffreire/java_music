package model;

public class Perfil {
    private String nome;
    private final String email;

    public Perfil(String nome, String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        this.email = email.trim().toLowerCase();
        setNome(nome);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() { return email; }

    public String getDescricao() {
        return "Perfil Genérico: " + getNome();
    }
    
    @Override
    public String toString() {
        return getNome() + " <" + getEmail() + ">";
    }
}