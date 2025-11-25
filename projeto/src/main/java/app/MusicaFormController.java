package app;

import exception.DadosInvalidosException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Musica;

public class MusicaFormController {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtArtista;
    @FXML private TextField txtAlbum;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDuracao;
    @FXML private Label lblErro;
    @FXML private Label lblTituloJanela;

    private Musica novaMusica = null; 
    
    private Musica musicaParaEditar = null; 
    
    private boolean salvo = false;

    public void setMusicaParaEditar(Musica musica) {
        this.musicaParaEditar = musica;
        
        lblTituloJanela.setText("Editar Música");

        txtTitulo.setText(musica.getTitulo());
        txtArtista.setText(musica.getArtista());
        txtAlbum.setText(musica.getAlbum());
        txtGenero.setText(musica.getGenero());
        txtDuracao.setText(String.valueOf(musica.getDuracaoSegundos()));
    }

    @FXML
    private void handleSalvar() {
        try {
            String titulo = txtTitulo.getText();
            String artista = txtArtista.getText();
            String album = txtAlbum.getText();
            String genero = txtGenero.getText();
            String duracaoTexto = txtDuracao.getText().trim();
            
            int duracao;
            try {
                duracao = Integer.parseInt(duracaoTexto);
            } catch (NumberFormatException e) {
                lblErro.setText("Erro: A duração deve ser um número inteiro (ex: 180).");
                return;
            }
            
            if (musicaParaEditar == null) {
                this.novaMusica = new Musica(titulo, artista, album, genero, duracao);
            } else {
                musicaParaEditar.setTitulo(titulo);
                musicaParaEditar.setArtista(artista);
                musicaParaEditar.setAlbum(album);
                musicaParaEditar.setGenero(genero);
                musicaParaEditar.setDuracaoSegundos(duracao);
            }

            this.salvo = true;
            closeWindow();

        } catch (DadosInvalidosException e) {
            lblErro.setText("Atenção: " + e.getMessage());
            
        } catch (Exception e) {
            e.printStackTrace();
            lblErro.setText("Erro inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        this.salvo = false;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) lblErro.getScene().getWindow();
        stage.close();
    }
    public Musica getMusicaSalva() {
        return (musicaParaEditar != null) ? musicaParaEditar : novaMusica;
    }

    public boolean isSalvo() {
        return salvo;
    }
}