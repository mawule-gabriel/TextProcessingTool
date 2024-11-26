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
import java.util.ArrayList;
import java.util.Set;


/**
 * The RegexController class handles the user interface and actions for text processing,
 * regex pattern search and replacement, and data management functionality.
 * It interacts with the TextProcessor and DataManager classes to perform tasks such as
 * searching and replacing text using regex, managing a list of people, and displaying statistics.
 */
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

    // Advanced Text Analysis
    @FXML private Label wordCountLabel;
    @FXML private Label charCountLabel;

    // Data Management
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private Button createPersonButton;
    @FXML private Button updatePersonButton;
    @FXML private Button deletePersonButton;
    @FXML private ListView<String> personListView;

    // Regex Examples
    @FXML private ListView<String> regexExamplesListView;
    @FXML private Button displayRegexExamplesButton;

    /**
     * Initializes the controller by setting up actions for buttons and updating UI elements.
     * This method is called when the FXML file is loaded and sets up event listeners for various actions.
     */
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

        // Set up action for displaying regex examples
        displayRegexExamplesButton.setOnAction(e -> displayRegexExamples());

        // Populate person list on initialization
        refreshPersonList();

        // Update text statistics initially
        updateTextStatistics();

        // Add listener to update stats as user types
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateTextStatistics());
    }

    /**
     * Displays predefined regex examples in the regexExamplesListView.
     * The examples are retrieved from the TextProcessor class.
     */
    private void displayRegexExamples() {
        ArrayList<String> regexExamples = textProcessor.getRegexExamples();
        regexExamplesListView.getItems().clear();
        if (regexExamples.isEmpty()) {
            regexExamplesListView.getItems().add("No examples available.");
        } else {
            regexExamplesListView.getItems().addAll(regexExamples);
        }
    }

    /**
     * Searches for matches of the regex pattern in the input text area and displays the results in the result list view.
     * If no matches are found, an error message is shown.
     */
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

    /**
     * Replaces occurrences of the regex pattern in the input text area with the replacement text.
     * The replaced text is displayed in the result text area.
     */
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

    /**
     * Clears all input and result fields, and updates the text statistics.
     */
    private void clearInput() {
        inputTextArea.clear();
        regexPatternField.clear();
        replacementField.clear();
        resultTextArea.clear();
        resultListView.getItems().clear();
        updateTextStatistics();  // Update stats when input is cleared
    }

    /**
     * Copies the result from the result text area or list view to the system clipboard.
     * If the result text area is empty, the content from the result list view is copied instead.
     */
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


    /**
     * Saves the result from the result text area or list view to a file selected by the user.
     * The file is saved with a .txt extension.
     */
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

    /**
     * Creates a new person with the given name and age, and adds them to the data manager.
     * Displays a success message and refreshes the person list.
     */
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
            displayUniquePersons(); // Refresh unique persons display
        } catch (NumberFormatException e) {
            showError("Age must be a valid number.");
        }
    }


    /**
     * Updates an existing person's information (name and age) in the data manager.
     * Displays a success message and refreshes the person list.
     */
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
            displayUniquePersons(); // Refresh unique persons display
        } catch (NumberFormatException e) {
            showError("Age must be a valid number.");
        }
    }


    /**
     * Deletes a person by name from the data manager and updates the person list.
     */
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
        displayUniquePersons(); // Refresh unique persons display
    }


    /**
     * Refreshes the list of all persons in the ListView, displaying their names and ages.
     */
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


    /**
     * Displays the list of unique person
     */
    public void displayUniquePersons() {
        // Clear the current items in the ListView
        Platform.runLater(() -> {
            personListView.getItems().clear();

            // Get the unique persons from the data manager
            Set<DataManager.Person> uniquePersons = dataManager.getUniquePersons();

            // Add each person's name and age to the ListView
            for (DataManager.Person person : uniquePersons) {
                personListView.getItems().add(person.getName() + " (" + person.getAge() + " years)");
            }
        });
    }

    /**
     * Updates the word count and character count labels based on the current text in the input text area.
     */
    private void updateTextStatistics() {
        String inputText = inputTextArea.getText();
        int wordCount = countWords(inputText);
        int charCount = inputText.length();

        // Update the labels with the calculated statistics
        wordCountLabel.setText("Word Count: " + wordCount);
        charCountLabel.setText("Character Count: " + charCount);
    }

    /**
     * Helper method to count the number of words in the given text.
     * A word is defined as a sequence of non-whitespace characters separated by spaces.
     *
     * @param text The text to analyze.
     * @return The number of words in the text.
     */
    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

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
