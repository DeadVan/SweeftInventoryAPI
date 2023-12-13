package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import static aquality.selenium.browser.AqualityServices.getLogger;

public class RandUtils {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz";
    public static final int NUM_LETTERS_IN_ALPHABET = 26;
    public static final int NUM_DIGITS = 10;
    public static String generateString(int length, int numCapitalLetters, int numNumbers) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        if (length < numCapitalLetters + numNumbers) {
            System.out.println("length must be higher than sum of other arguments");
        } else {
            for (int i = 0; i < length - numCapitalLetters - numNumbers; i++) {
                int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
                char c = ALPHA_NUMERIC_STRING.charAt(index);
                builder.append(c);
            }
            for (int i = 0; i < numCapitalLetters; i++) {
                char c = (char) (random.nextInt(NUM_LETTERS_IN_ALPHABET) + 'A');
                builder.append(c);
            }
            for (int i = 0; i < numNumbers; i++) {
                int num = random.nextInt(NUM_DIGITS);
                builder.append(num);
            }
        }
        return builder.toString();
    }
    public static String convertImageToBase64(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            getLogger().info(e.getMessage());
            throw new RuntimeException("Error converting image to Base64");
        }
    }
}
