package com.example.text_editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println(this.getClass().getResource("/com/example/text_editor/TextView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/text_editor/TextView.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Text Processing Tool");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
