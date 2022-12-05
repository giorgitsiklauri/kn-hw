package org.kn.hw.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractiveConsole {

    public static String acceptCurrency() {
        Scanner cur = new Scanner(System.in);
        System.out.println("Please enter currency (USD, EUR, GBP, etc.):");

        String res = cur.next();

        while (!res.matches("[a-zA-Z]{3}")) {
            System.out.println("Wrong currency format. Use Alpha-3 ISO format.");
            res = cur.next();
        }
        return res;
    }

    public static boolean keepOn() {
        int choice = -1;
        while (choice == -1) {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice!=1 && choice!=0) {
                    choice = -1;
                    throw new IllegalArgumentException();
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Invalid argument. Please enter 1 or 0: ");
            }
        }
        return choice == 1;
    }
}
