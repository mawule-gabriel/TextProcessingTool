package org.example.textprocessingtool;

import java.util.regex.*;
import java.util.ArrayList;

public class RegexProcessor {

    /**
     * Searches for all matches of the given regex pattern in the provided text.
     *
     * @param text The text in which to search for matches.
     * @param pattern The regex pattern to match.
     * @return A list of matched strings.
     */
    public ArrayList<String> search(String text, String pattern) {
        ArrayList<String> matches = new ArrayList<>();

        try {

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(text);


            while (matcher.find()) {
                matches.add(matcher.group());
            }
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid regex pattern: " + e.getDescription());
        }

        return matches;
    }

    /**
     * Replaces all occurrences of the regex pattern in the text with the replacement string.
     *
     * @param text The text in which to replace matches.
     * @param pattern The regex pattern to match.
     * @param replacement The string to replace matches with.
     * @return The text with the replacements.
     */
    public String replace(String text, String pattern, String replacement) {
        try {

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(text);

            // Replace all matches
            return matcher.replaceAll(replacement);  // Return the replaced text
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid regex pattern: " + e.getDescription());
            return text;  // If regex is invalid, return original text
        }
    }

    /**
     * Validates if the provided regex pattern is correct.
     *
     * @param pattern The regex pattern to validate.
     * @return true if the pattern is valid, false otherwise.
     */
    public boolean isValidRegex(String pattern) {
        try {

            Pattern.compile(pattern);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    /**
     * Provides examples of regex features that this module supports.
     * This is useful for understanding how regex can be structured and used.
     *
     * @return A list of regex examples for common patterns.
     */
    public ArrayList<String> getRegexExamples() {
        ArrayList<String> examples = new ArrayList<>();


        examples.add("[a-z]      : Match a lowercase letter");
        examples.add("[A-Z]      : Match an uppercase letter");
        examples.add("\\d        : Match any digit (0-9)");
        examples.add("\\D        : Match any non-digit character");
        examples.add("\\w        : Match any word character (alphanumeric + underscore)");
        examples.add("\\W        : Match any non-word character");
        examples.add("\\s        : Match any whitespace character (space, tab, newline)");
        examples.add("\\S        : Match any non-whitespace character");
        examples.add(".*        : Match any string (including empty string) of any length");
        examples.add("[a-z]+    : Match one or more lowercase letters");
        examples.add("\\bword\\b : Match the word 'word' as a whole word (word boundary)");
        examples.add("[a-zA-Z0-9]+ : Match alphanumeric characters (letters and digits) one or more times");

        return examples;
    }

    /**
     * Example of using sets, ranges, and quantifiers in regex.
     *
     * @param text The text to apply the example regex.
     * @return Example results for the user.
     */
    public ArrayList<String> regexFeaturesExamples(String text) {
        ArrayList<String> result = new ArrayList<>();

        // Set and Range examples
        result.add("Digits in the text: " + search(text, "\\d+"));  // Match digits
        result.add("Lowercase letters in the text: " + search(text, "[a-z]+"));  // Match lowercase letters
        result.add("Uppercase letters in the text: " + search(text, "[A-Z]+"));  // Match uppercase letters
        result.add("Words with exactly 4 letters: " + search(text, "\\b[a-zA-Z]{4}\\b"));  // Match exactly 4 letter words

        // Quantifiers examples
        result.add("Words starting with 't': " + search(text, "\\bt\\w+\\b"));  // Match words starting with 't'
        result.add("Non-space characters: " + search(text, "\\S+"));  // Match non-space characters

        return result;
    }
}
