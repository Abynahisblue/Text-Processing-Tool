package com.example.text_editor;

import com.example.text_editor.controller.TextController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader regexLoader = new FXMLLoader(getClass().getResource("/com/example/text_editor/TextView.fxml"));
            VBox root = new VBox();
            root.getChildren().add(regexLoader.load());

            TextController controller = regexLoader.getController();
            controller.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Text Processing and Data Management Tool");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
