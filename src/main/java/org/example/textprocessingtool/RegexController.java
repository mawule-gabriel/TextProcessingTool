package org.example.textprocessingtool;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RegexController {

    private TextProcessor textProcessor;
    private DataManager dataManager;

    // Text Processing
    @FXML private TextArea inputTextArea;
    @FXML private TextField regexPatternField;
    @FXML private TextField replacementField;
    @FXML private Button searchButton;
    @FXML private Button replaceButton;
    @FXML private Button clearInputButton;
    @FXML private Button copyResultButton;
    @FXML private Button saveResultButton;
    @FXML private ListView<String> resultListView;
    @FXML private TextArea resultTextArea;

    // Data Management
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private Button createPersonButton;
    @FXML private Button updatePersonButton;
    @FXML private Button deletePersonButton;
    @FXML private ListView<String> personListView;

    public void initialize() {
        // Initialize the controllers
        textProcessor = new TextProcessor();
        dataManager = new DataManager();

        // Set up actions for text processing buttons
        searchButton.setOnAction(e -> performSearch());
        replaceButton.setOnAction(e -> performReplace());
        clearInputButton.setOnAction(e -> clearInput());
        copyResultButton.setOnAction(e -> copyResultToClipboard());
        saveResultButton.setOnAction(e -> saveResultToFile());

        // Set up actions for data management buttons
        createPersonButton.setOnAction(e -> createPerson());
        updatePersonButton.setOnAction(e -> updatePerson());
        deletePersonButton.setOnAction(e -> deletePerson());

        // Populate person list on initialization
        refreshPersonList();
    }

    // Text Processing methods
    private void performSearch() {
        String text = inputTextArea.getText();
        String pattern = regexPatternField.getText();

        if (pattern.isEmpty()) {
            showError("Please enter a valid regex pattern.");
            return;
        }

        var searchResults = textProcessor.searchText(text, pattern);
        resultListView.getItems().clear();

        if (searchResults.isEmpty()) {
            resultListView.getItems().add("No matches found.");
        } else {
            resultListView.getItems().addAll(searchResults);
        }
    }

    private void performReplace() {
        String text = inputTextArea.getText();
        String pattern = regexPatternField.getText();
        String replacement = replacementField.getText();

        if (pattern.isEmpty()) {
            showError("Please enter a valid regex pattern.");
            return;
        }

        String replacedText = textProcessor.replaceText(text, pattern, replacement);
        resultTextArea.setText(replacedText);
    }

    private void clearInput() {
        inputTextArea.clear();
        regexPatternField.clear();
        replacementField.clear();
        resultTextArea.clear();
        resultListView.getItems().clear();
    }

    private void copyResultToClipboard() {
        String resultText = resultTextArea.getText().isEmpty()
                ? String.join("\n", resultListView.getItems())
                : resultTextArea.getText();

        if (!resultText.isEmpty()) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(resultText);
            clipboard.setContent(content);
            showInfo("Result copied to clipboard!");
        }
    }

    private void saveResultToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Result");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                String resultText = resultTextArea.getText().isEmpty()
                        ? String.join("\n", resultListView.getItems())
                        : resultTextArea.getText();

                Files.writeString(file.toPath(), resultText);
                showInfo("Result saved successfully!");
            } catch (IOException e) {
                showError("Failed to save file: " + e.getMessage());
            }
        }
    }

    // Data Management methods
    private void createPerson() {
        String name = nameField.getText();
        String ageText = ageField.getText();

        if (name.isEmpty() || ageText.isEmpty()) {
            showError("Please enter both name and age.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            dataManager.createPerson(name, age);
            showInfo("Person created: " + name);
            nameField.clear();
            ageField.clear();
            refreshPersonList();
        } catch (NumberFormatException e) {
            showError("Age must be a valid number.");
        }
    }

    private void updatePerson() {
        String name = nameField.getText();
        String ageText = ageField.getText();

        if (name.isEmpty() || ageText.isEmpty()) {
            showError("Please enter both name and age.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            dataManager.updatePerson(name, age);
            showInfo("Person updated: " + name);
            nameField.clear();
            ageField.clear();
            refreshPersonList();
        } catch (NumberFormatException e) {
            showError("Age must be a valid number.");
        }
    }

    private void deletePerson() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            showError("Please enter the name of the person to delete.");
            return;
        }

        dataManager.deletePerson(name);
        showInfo("Person deleted: " + name);
        nameField.clear();
        refreshPersonList();
    }

    private void refreshPersonList() {
        Platform.runLater(() -> {
            personListView.getItems().clear();
            personListView.getItems().addAll(
                    dataManager.getAllPersons().stream()
                            .map(p -> p.getName() + " (" + p.getAge() + " years)")
                            .toList()
            );
        });
    }

    // Helper methods
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}