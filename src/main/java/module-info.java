module com.example.text_editor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.textEditor to javafx.fxml;
    exports com.example.textEditor;
    exports com.example.textEditor.controller;
    opens com.example.textEditor.controller to javafx.fxml;
    exports com.example.textEditor.model;
    opens com.example.textEditor.model to javafx.fxml;
}