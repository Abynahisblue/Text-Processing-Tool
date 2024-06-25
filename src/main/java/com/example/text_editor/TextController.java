package com.example.text_editor;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextController {
    @FXML
    private TextArea textInput;
    @FXML
    private TextField regexInput;
    @FXML
    private TextField replaceInput;
    @FXML
    private TextArea resultArea;

    @FXML
    private void  handleSearch(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            StringBuilder result = new StringBuilder("Matches found at positions: \n");
            while (matcher.find()) {
                result.append("Start: ").append(matcher.start()).append(", End: ").append(matcher.end()).append("\n");
            }
            resultArea.setText(result.toString());
        } catch (Exception e){
            resultArea.setText("Invalid regex pattern" + e.getMessage());
        }
    }
    @FXML
    private void handleMatch(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        try {
            boolean isMatch = Pattern.matches(regex, text);
            resultArea.setText("Matches entire text: " + isMatch);
        } catch (Exception e){
            resultArea.setText("Invalid regex pattern" + e.getMessage());
        }
    }

    @FXML
    private void handleReplace(){
        String text = textInput.getText();
        String regex = regexInput.getText();
        String replacement = replaceInput.getText();
        try {
            String result = text.replaceAll(regex, replacement);
            resultArea.setText("Results after replacement:\n" + result);
        } catch (Exception e){
            resultArea.setText("Invalid regex pattern" + e.getMessage());
        }
    }
}
