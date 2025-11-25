package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import repository.UsuarioRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblErro;

    private final UsuarioRepository users = new UsuarioRepository();
    
    public LoginController() {
        users.cadastrar(new Usuario("Admin", "admin@email.com", "1234"));
    }
    @FXML
    protected void handleLogin() {
        String email = txtEmail.getText().trim().toLowerCase();
        String senha = txtSenha.getText().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Email e senha são obrigatórios.");
            return;
        }

        Optional<Usuario> opt = users.autenticar(email, senha);

        if (opt.isPresent()) {
            // Sucesso!
            lblErro.setText("Bem-vindo, " + opt.get().getNome() + "!");
            abrirTelaPrincipal();
        } else {
            // Falha
            lblErro.setText("Credenciais inválidas. Tente novamente.");
        }
    }

    @FXML
    protected void handleRegistrar() {
        String email = txtEmail.getText().trim().toLowerCase();
        String senha = txtSenha.getText().trim();
        String nome = "Usuário Padrão"; 

        try {
            if (users.existePorEmail(email)) {
                lblErro.setText("Já existe usuário com esse email.");
                return;
            }
            Usuario u = new Usuario(nome, email, senha);
            boolean ok = users.cadastrar(u);
            lblErro.setText(ok ? "Usuário registrado com sucesso." : "Não foi possível registrar.");
        } catch (IllegalArgumentException ex) {
            lblErro.setText("Erro: " + ex.getMessage());
        }
    }

    private void abrirTelaPrincipal() {
        try {
            URL fxmlUrl = getClass().getResource("musica-view.fxml");
            
            if (fxmlUrl == null) {
                lblErro.setText("Erro: Não foi possível encontrar a tela principal.");
                return;
            }
            
            Parent root = FXMLLoader.load(fxmlUrl);
            Stage stage = new Stage();
            stage.setTitle("Mini Biblioteca de Músicas");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) lblErro.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            lblErro.setText("Erro ao abrir tela principal: " + e.getMessage());
        }
    }
}