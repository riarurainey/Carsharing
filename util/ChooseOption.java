package carsharing.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ChooseOption {
    public static int getScanNumber() {
        int number = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            number = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Incorrect input");
        }

        return number;
    }

    public static String getScanString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }
}
