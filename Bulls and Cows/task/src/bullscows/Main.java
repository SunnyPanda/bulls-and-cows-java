package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int digits = in.nextInt();
        if (digits > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %s because there aren't enough " +
                    "unique digits.\n", digits);
            return;
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

        System.out.printf("The random secret number is %s.\n", number);
    }

    private static void grader() {
        String code = "9305";

        Scanner in = new Scanner(System.in);
        String guess = in.nextLine();

        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == code.charAt(i)) bulls++;
            else if (code.contains("" + guess.charAt(i))) cows++;
        }

        String result;
        if (bulls == 0 && cows == 0) result = "None";
        else if (bulls == 0) result = String.format("%d cow(s)", cows);
        else if (cows == 0) result = String.format("%d bull(s)", bulls);
        else result = String.format("%d bull(s) and %d cow(s)", bulls, cows);
        System.out.printf("Grade: %s. The secret code is 9305.\n", result);
    }
}
