package citywars.finalproject.Model;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

    // Generate a random password with the specified length
    public static String generatePassword(int length) {
        // Define character sets for each required type of character
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitChars = "0123456789";
        String specialChars = "@$!%*?&";

        // Create a string builder to construct the password
        StringBuilder passwordBuilder = new StringBuilder();

        // Add at least one character from each character set
        passwordBuilder.append(lowercaseChars.charAt(new SecureRandom().nextInt(lowercaseChars.length())));
        passwordBuilder.append(uppercaseChars.charAt(new SecureRandom().nextInt(uppercaseChars.length())));
        passwordBuilder.append(digitChars.charAt(new SecureRandom().nextInt(digitChars.length())));
        passwordBuilder.append(specialChars.charAt(new SecureRandom().nextInt(specialChars.length())));

        // Fill the rest of the password with random characters from all character sets
        for (int i = 4; i < length; i++) {
            String allChars = lowercaseChars + uppercaseChars + digitChars + specialChars;
            passwordBuilder.append(allChars.charAt(new SecureRandom().nextInt(allChars.length())));
        }

        // Shuffle the characters in the password to randomize their order
        String password = passwordBuilder.toString();
        char[] passwordArray = password.toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = new SecureRandom().nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }
        return new String(passwordArray);
    }
}
