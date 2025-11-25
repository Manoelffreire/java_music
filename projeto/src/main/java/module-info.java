module minibiblioteca {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens app to javafx.fxml, javafx.graphics;
    
    opens model to javafx.base;
}