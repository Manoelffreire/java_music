package model;

public class Convidado extends Perfil {

    public Convidado(String nome, String email) {
        super(nome, email);
    }
    @Override
    public String getDescricao() {
        return "Convidado (Acesso Limitado): " + getNome();
    }

    @Override
    public String toString() {
        return "Convidado: " + super.toString();
    }
}