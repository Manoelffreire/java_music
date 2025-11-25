package demo;
import model.Convidado;
import model.Perfil;
import model.Usuario;
import java.util.ArrayList;

public class TestePolimorfismo {

    public static void main(String[] args) {
        
        System.out.println("Herança e Polimorfismo");

        ArrayList<Perfil> minhaListaDePerfis = new ArrayList<>();

        minhaListaDePerfis.add(
            new Usuario("Administrador", "admin@email.com", "senhaForte123")
        );
        
        minhaListaDePerfis.add(
            new Convidado("Visitante", "visita@site.com")
        );
        
        minhaListaDePerfis.add(
            new Usuario("Usuario Comum", "comum@email.com", "4321")
        );

        System.out.println("\nIterando sobre a lista polimórfica:");

        for (Perfil item : minhaListaDePerfis) {
            
            System.out.println(item.getDescricao());
            System.out.println("   -> " + item.toString()); 
            System.out.println();
        }
    }
}