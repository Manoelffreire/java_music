package model;

public class Usuario extends Perfil {
    
    private String senha;

    public Usuario(String nome, String email, String senha) {
        
        super(nome, email);
        setSenha(senha);
    }


    public String getSenha() { return senha; }
    public void setSenha(String senha) {
        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 4 caracteres.");
        }
        this.senha = senha;
    }

    public boolean autenticar(String email, String senha) {

        return this.getEmail().equalsIgnoreCase(email) && this.senha.equals(senha);
    }

    @Override
    public String getDescricao() {

        return "Usuário Autenticável: " + getNome();
    }
    
    @Override
    public String toString() {

        return "Usuário: " + super.toString();
    }
}