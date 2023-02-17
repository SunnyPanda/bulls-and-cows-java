package bullscows;

import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String secretCode = generateCode();
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        System.out.printf("Turn %d:\n", turn);
        String guess = in.next();
        while (!grader(secretCode, guess)) {
            turn++;
            System.out.printf("Turn %d:\n", turn);
            guess = in.next();
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static String generateCode() {
        int digits = in.nextInt();
        while (digits > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %s because there aren't enough " +
                    "unique digits.\n", digits);
            System.out.println("Please, enter the secret code's length:");
            digits = in.nextInt();
        }

        StringBuilder number = new StringBuilder();
        StringBuilder pseudoRandomNumber = new StringBuilder(String.valueOf(System.nanoTime())).reverse();
        for (int i = 0; number.length() < digits; i++) {
            if (i < pseudoRandomNumber.length()) {
                char digit = pseudoRandomNumber.charAt(i);
                String strDigit = Character.getNumericValue(digit) == 0 && number.length() == 0 ? "" : "" + digit;
                if (!number.toString().contains(strDigit)) number.append(strDigit);
            } else {
                pseudoRandomNumber = new StringBuilder(String.valueOf(System.nanoTime())).reverse();
                i = 0;
            }
        }
        return number.toString();
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
