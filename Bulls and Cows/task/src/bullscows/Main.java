package bullscows;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Scanner IN = new Scanner(System.in);
    private static final int LOWER_INT = 0;
    private static final char LOWER_CHAR = '0';
    private static final int MAX_NUMBER_OF_DIGITS = 10;
    private static final char MIN_LETTER = 'a';
    private static final int MAX_DIGIT = 9;


    public static void main(String[] args) {
        String secretCode = generateCode();
        if (secretCode == null) return;

        System.out.println("Okay, let's start a game!");

        int turn = 1;
        System.out.printf("Turn %d:\n", turn);
        String guess = IN.next();
        while (!grader(secretCode, guess)) {
            turn++;
            System.out.printf("Turn %d:\n", turn);
            guess = IN.next();
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static String generateCode() {
        String digitsStr = IN.next();
        int digits = isNumber(digitsStr) ? Integer.parseInt(digitsStr) : 0;
        if (digits == 0) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", digitsStr);
            return null;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbols = IN.nextInt();

        if (possibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        }
        if (digits > possibleSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n",
                    digits, possibleSymbols);
            return null;
        }

        int diff = possibleSymbols - MAX_NUMBER_OF_DIGITS;
        String code;
        String pattern;
        if (diff <= 0) {
            pattern = String.format("%d-%d", LOWER_INT, possibleSymbols - 1);
            code = generateNumberCode(digits, possibleSymbols);
        } else {
            pattern = String.format("%d-%d, %c-%c", LOWER_INT, MAX_DIGIT, MIN_LETTER, (char)(MIN_LETTER + diff - 1));
            code = generateNumAndLetterCode(digits, diff);
        }

        System.out.printf("The secret is prepared: %s (%s).\n", "*".repeat(digits), pattern);

        return code;
    }

    private static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(str).matches();
    }

    private static String generateNumberCode(int digits, int possibleSymbols) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        while (code.length() < digits) {
            char number = (char)(random.nextInt(possibleSymbols) + LOWER_CHAR);
            if (code.indexOf(String.valueOf(number)) == -1) code.append(number);
        }
        return code.toString();
    }

    private static String generateNumAndLetterCode(int digits, int diff) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        int upper = LOWER_CHAR * 2 + diff;
        while (code.length() < digits) {
            char number = (char) (random.nextInt(upper - LOWER_CHAR) + LOWER_CHAR);
            if (number <= MAX_DIGIT || number >= MIN_LETTER)
                if (code.indexOf(String.valueOf(number)) == -1) code.append(number);
        }
        return code.toString();
    }

    private static boolean grader(String code, String guess) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < code.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) bulls++;
            else if (code.contains("" + guess.charAt(i))) cows++;
        }

        String result;
        if (bulls == 0 && cows == 0) result = "None";
        else if (bulls == 0) result = String.format("%d cow(s)", cows);
        else if (cows == 0) result = String.format("%d bull(s)", bulls);
        else result = String.format("%d bull(s) and %d cow(s)", bulls, cows);
        System.out.printf("Grade: %s.\n", result);

        return bulls == code.length();
    }
}
