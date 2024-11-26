package org.example.textprocessingtool;

import java.util.ArrayList;

public class TextProcessor {

    private RegexProcessor regexProcessor; // The RegexProcessor instance to handle regex operations

    // Constructor to initialize RegexProcessor
    public TextProcessor() {
        this.regexProcessor = new RegexProcessor();
    }

    /**
     * Searches for a pattern in the text and returns all matches.
     *
     * @param text The text in which to search.
     * @param pattern The regex pattern to match.
     * @return A list of matched strings.
     */
    public ArrayList<String> searchText(String text, String pattern) {
        // Utilize RegexProcessor's search method
        return regexProcessor.search(text, pattern);
    }




    /**
     * Replaces all occurrences of a pattern in the text with a given replacement.
     *
     * @param text The text in which to perform the replacement.
     * @param pattern The regex pattern to match.
     * @param replacement The string to replace the matches with.
     * @return The text with replacements.
     */
    public String replaceText(String text, String pattern, String replacement) {
        // Utilize RegexProcessor's replace method
        return regexProcessor.replace(text, pattern, replacement);
    }




    /**
     * Validates if the regex pattern is valid.
     *
     * @param pattern The regex pattern to validate.
     * @return True if the pattern is valid, false otherwise.
     */
    public boolean validateRegexPattern(String pattern) {
        // Utilize RegexProcessor's isValidRegex method
        return regexProcessor.isValidRegex(pattern);
    }

    /**
     * Provides examples of common regex patterns and their descriptions.
     *
     * @return A list of regex examples with descriptions.
     */
    public ArrayList<String> getRegexExamples() {
        // Utilize RegexProcessor's getRegexExamples method
        return regexProcessor.getRegexExamples();
    }

    /**
     * Displays regex feature examples applied to the given text.
     *
     * @param text The text on which to apply regex features.
     * @return A list of results showing regex features applied to the text.
     */
    public ArrayList<String> displayRegexFeatureExamples(String text) {
        // Utilize RegexProcessor's regexFeaturesExamples method
        return regexProcessor.regexFeaturesExamples(text);
    }

    /**
     * Provides a user-friendly display of the results of search and replace operations.
     *
     * @param searchResults The list of matched strings from a search.
     * @param replacedText The modified text after performing replacements.
     */
    public void displayResults(ArrayList<String> searchResults, String replacedText) {
        // Displaying the search results
        if (searchResults.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            System.out.println("Matches found:");
            for (String match : searchResults) {
                System.out.println("- " + match);
            }
        }

        // Displaying the replaced text
        System.out.println("\nReplaced text:");
        System.out.println(replacedText);
    }
}
