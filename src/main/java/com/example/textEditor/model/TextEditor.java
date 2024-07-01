package com.example.textEditor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextEditor {
    private final List<String> content;

    public TextEditor(){
        content = new ArrayList<>();
    }



    public List<String> getContent() {
        return content;
    }

    public void addText(String text) {
        content.add(text);
    }

    public void updateText(int index, String newText) {
        if (index >= 0 && index < content.size()) {
            content.set(index, newText);
        }
    }

    public void replaceText(String regex, String replacement) {
        for (int i = 0; i < content.size(); i++) {
            content.set(i, content.get(i).replaceAll(regex, replacement));
        }
    }

public void deleteText(int index) {
    if (index >= 0 && index < content.size()) {
        content.remove(index);
    }
}

@Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TextEditor that = (TextEditor) obj;
        return Objects.equals(content, that.content);
    }

    @Override
    public String toString() {
        return String.join("\n", content);
    }
}
