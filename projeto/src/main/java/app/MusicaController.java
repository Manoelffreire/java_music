package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Musica;
import repository.BibliotecaMusical;
import java.io.IOException;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class MusicaController {

    @FXML
    private TableView<Musica> tabelaMusicas;
    @FXML
    private TableColumn<Musica, String> colTitulo;
    @FXML
    private TableColumn<Musica, String> colArtista;
    @FXML
    private TableColumn<Musica, String> colAlbum;
    @FXML
    private TableColumn<Musica, String> colGenero;
    @FXML
    private TableColumn<Musica, Integer> colDuracao;

    @FXML
    private TextField txtFiltro;

    private final BibliotecaMusical repo = new BibliotecaMusical();
    private final ObservableList<Musica> musicasVisiveis = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoSegundos"));
        
        handleAtualizar();
        
        FilteredList<Musica> listaFiltrada = new FilteredList<>(musicasVisiveis, p -> true);
        
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(musica -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String termo = newValue.toLowerCase();

                if (musica.getTitulo().toLowerCase().contains(termo)) {
                    return true;
                } else if (musica.getArtista().toLowerCase().contains(termo)) {
                    return true;
                } else if (musica.getAlbum().toLowerCase().contains(termo)) {
                    return true;
                } else if (musica.getGenero().toLowerCase().contains(termo)) {
                    return true;
                }
                
                return false;
            });
        });
        
        tabelaMusicas.setItems(listaFiltrada);
    }

    @FXML
    protected void handleAtualizar() {
        musicasVisiveis.clear();
        musicasVisiveis.addAll(repo.listarTodas());
    }

    @FXML
    protected void handleAdicionar() {
        try {
            URL fxmlUrl = getClass().getResource("musica-form-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            MusicaFormController formController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Adicionar Nova Música");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaMusicas.getScene().getWindow()); 

            stage.showAndWait();

            if (formController.isSalvo()) {
                Musica novaMusica = formController.getMusicaSalva();
                
                if (!repo.adicionarMusica(novaMusica)) {
                    showAlert(AlertType.WARNING, "Falha ao Adicionar", "Já existe uma música idêntica cadastrada.");
                }
                
                handleAtualizar();
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Falha ao abrir o formulário de adição: " + e.getMessage());
        }
    }

    @FXML
    protected void handleEditar() {
        Musica selecionada = tabelaMusicas.getSelectionModel().getSelectedItem();
        
        if (selecionada == null) {
            showAlert(AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione uma música na tabela para editar.");
            return;
        }

        try {
            URL fxmlUrl = getClass().getResource("musica-form-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            MusicaFormController formController = loader.getController();
            formController.setMusicaParaEditar(selecionada);

            Stage stage = new Stage();
            stage.setTitle("Editar Música");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tabelaMusicas.getScene().getWindow());

            stage.showAndWait();

            if (formController.isSalvo()) {
                Musica musicaEditada = formController.getMusicaSalva();
                
                repo.editarMusica(
                    musicaEditada.getId(),
                    musicaEditada.getTitulo(),
                    musicaEditada.getArtista(),
                    musicaEditada.getAlbum(),
                    musicaEditada.getGenero(),
                    musicaEditada.getDuracaoSegundos()
                );
                
                tabelaMusicas.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Falha ao abrir o formulário de edição: " + e.getMessage());
        }
    }

    @FXML
    protected void handleRemover() {
        Musica selecionada = tabelaMusicas.getSelectionModel().getSelectedItem();
        
        if (selecionada == null) {
            showAlert(AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione uma música na tabela para remover.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Remover Música");
        alert.setContentText("Tem certeza que deseja remover a música: " + selecionada.getTitulo() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean ok = repo.removerPorId(selecionada.getId());
            if (ok) {
                handleAtualizar(); 
            } else {
                showAlert(AlertType.ERROR, "Erro", "Ocorreu um erro ao tentar remover a música.");
            }
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}