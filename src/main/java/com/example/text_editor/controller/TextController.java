package com.example.text_editor.controller;

import com.example.text_editor.model.TextEditor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextController {
    private static final int MAX_DISPLAY_LENGTH = 50;

    @FXML
    private TextArea textInput;
    @FXML
    private TextField regexInput;
    @FXML
    private TextField replaceInput;
    @FXML
    private TextFlow resultArea;
    @FXML
    private TextField indexInput;
    @FXML
    private ListView<String> textListView;

    private TextEditor textEditor;
    private ObservableList<String> textList;

    private Stage primaryStage;

    public void initialize() {
        textList = FXCollections.observableArrayList(textEditor.getContent());
        textListView.setItems(textList);
        textListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.length() > MAX_DISPLAY_LENGTH
                                    ? item.substring(0, MAX_DISPLAY_LENGTH) + "..."
                                    : item);
                        }
                    }
                };
            }
        });

        textListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textInput.setText(newValue);
            }
        });
    }



    public TextController() {
        textEditor =new TextEditor();
        textList = FXCollections.observableArrayList(textEditor.getContent());
    }

    public void setTextInput(String content) {
        textInput.setText(content);
    }

    @FXML
    public void handleAdd() {
        String text = textInput.getText();
        textEditor.addText(text);
        textList.setAll(textEditor.getContent());
        clearInputs();
        resultArea.getChildren().clear();
    }


    @FXML
    private void  handleSearch(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            TextFlow textFlow = new TextFlow();
            int lastIndex = 0;

            while(matcher.find()){
                if (matcher.start() > lastIndex){
                    Text normalText = new Text(text.substring(lastIndex, matcher.start()));
                    textFlow.getChildren().add(normalText);
                }
                Text highlightedText = new Text(text.substring(matcher.start(), matcher.end()));
                highlightedText.setStyle("-fx-fill: blue; -fx-font-weight: bold;");
                textFlow.getChildren().add(highlightedText);
                lastIndex = matcher.end();

            }
            if(lastIndex < text.length()){
                Text remainingText = new Text(text.substring(lastIndex));
                textFlow.getChildren().add(remainingText);
            }
            resultArea.getChildren().clear();
            resultArea.getChildren().addAll(textFlow.getChildren());

        } catch (Exception e){
            resultArea.getChildren().clear();
            resultArea.getChildren().add(new Text("Invalid regex pattern" + e.getMessage()));
        }
    }
    @FXML
    private void handleMatch(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        try {
            boolean isMatch = Pattern.matches(regex, text);
            resultArea.getChildren().add(new Text("Matches entire text: " + isMatch));
        } catch (Exception e){
            resultArea.getChildren().add(new Text("Invalid regex pattern" + e.getMessage()));
        }
    }

    @FXML
    private void handleReplaceFirst() {
        String text = textInput.getText();
        String regex = regexInput.getText();
        String replacement = replaceInput.getText();

        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            StringBuilder replacedText = new StringBuilder();
            TextFlow textFlow = new TextFlow();
            int lastIndex = 0;

            while (matcher.find()) {
                if (matcher.start() > lastIndex) {
                    Text normalText = new Text(text.substring(lastIndex, matcher.start()));
                    textFlow.getChildren().add(normalText);
                }
                Text replacedPart = new Text(replacement);
                replacedPart.setStyle("-fx-fill: green; -fx-font-weight: bold;");
                textFlow.getChildren().add(replacedPart);
                replacedText.append(text, lastIndex, matcher.start());
                replacedText.append(replacement);
                lastIndex = matcher.end();
            }
            if (lastIndex < text.length()) {
                Text remainingText = new Text(text.substring(lastIndex));
                textFlow.getChildren().add(remainingText);
                replacedText.append(text.substring(lastIndex));
            }

            resultArea.getChildren().clear();
            resultArea.getChildren().addAll(textFlow.getChildren());

            // Update the text in the TextEditor
            textEditor.replaceText(regex, replacement);
            textList.setAll(textEditor.getContent());
        } catch (Exception e) {
            resultArea.getChildren().clear();
            resultArea.getChildren().add(new Text("Invalid regex pattern: " + e.getMessage()));
        }
    }

    @FXML
    private void handleReplace(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        String replacement = replaceInput.getText();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            StringBuilder replacedText = new StringBuilder();
            TextFlow textFlow = new TextFlow();
            int lastIndex = 0;

            while (matcher.find()) {
                if (matcher.start() > lastIndex) {
                    Text normalText = new Text(text.substring(lastIndex, matcher.start()));
                    textFlow.getChildren().add(normalText);
                }
                Text replacedPart = new Text(replacement);
                replacedPart.setStyle("-fx-fill: green; -fx-font-weight: bold;");
                textFlow.getChildren().add(replacedPart);
                replacedText.append(text, lastIndex, matcher.start());
                replacedText.append(replacement);
                lastIndex = matcher.end();
            }
            if (lastIndex < text.length()) {
                Text remainingText = new Text(text.substring(lastIndex));
                textFlow.getChildren().add(remainingText);
                replacedText.append(text.substring(lastIndex));
            }

            resultArea.getChildren().clear();
            resultArea.getChildren().addAll(textFlow.getChildren());

            // Update the text in the TextEditor
            textEditor.replaceText(regex, replacement);
            textList.setAll(textEditor.getContent());
        } catch (Exception e){
            resultArea.getChildren().add(new Text("Invalid regex pattern" + e.getMessage()));
        }
    }

    @FXML
    private void handleFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                String content = Files.readString(file.toPath());
                textInput.setText(content);
            } catch (IOException e) {
                resultArea.getChildren().clear();
                resultArea.getChildren().add(new Text("Error reading file: " + e.getMessage()));
            }
        }
    }

    @FXML
    private void handleUpdate() {
        String newText = textInput.getText();
        int index;
        try {
            index = Integer.parseInt(indexInput.getText());
        } catch (NumberFormatException e) {
            resultArea.getChildren().clear();
            resultArea.getChildren().add(new Text("Invalid index format"));
            resultArea.getChildren().clear();
            return;
        }

        if (index >= 0 && index < textEditor.getContent().size()) {
            textEditor.updateText(index, newText);
            textList.setAll(textEditor.getContent());
            clearInputs();
        } else {
            resultArea.getChildren().clear();
            resultArea.getChildren().add(new Text("Index out of bounds"));
        }
    }

    @FXML
    private void handleDelete() {
        int index;
        try {
            index = Integer.parseInt(indexInput.getText());
        } catch (NumberFormatException e) {
            resultArea.getChildren().clear();
            resultArea.getChildren().add(new Text("Invalid index format"));
            return;
        }
        textEditor.deleteText(index);
        textList.setAll(textEditor.getContent());
        clearInputs();
    }

    private void clearInputs() {
        textInput.clear();
        regexInput.clear();
        replaceInput.clear();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
