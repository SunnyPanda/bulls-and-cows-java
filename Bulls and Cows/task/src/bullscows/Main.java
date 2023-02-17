package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
