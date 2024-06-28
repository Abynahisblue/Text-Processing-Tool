module com.example.text_editor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.text_editor to javafx.fxml;
    exports com.example.text_editor;
    exports com.example.text_editor.controller;
    opens com.example.text_editor.controller to javafx.fxml;
    exports com.example.text_editor.model;
    opens com.example.text_editor.model to javafx.fxml;
}