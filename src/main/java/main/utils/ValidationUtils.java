package main.utils;

import main.models.exceptions.InvalidAmountException;

import java.util.Scanner;

public class ValidationUtils {


    public int intInput(Scanner scanner, int max) {
        int input;
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.print("Invalid integer, try again: ");
                scanner.nextLine(); // Consume the invalid input
                continue;  // Continue prompting until a valid integer is entered
            }
            input = scanner.nextInt();
            if (input <= 0 || input > max) {
                System.out.print("Invalid number, choose a number in the specified range (1 - " + (max) + "). Try again: ");
            } else {
                break;  // Exit the loop if the input is valid
            }
        }
        scanner.nextLine();  // Clear the buffer
        return input;
    }

    public double doubleInput(Scanner scanner) {
        return doubleInput(scanner,1, "Invalid amount, should be greater than 0 try again...");
    }

    // Validate double input with a minimum value and a custom message
    public double doubleInput(Scanner scanner, double min, String msg) {
        try {
            if (!scanner.hasNextDouble()) {
                scanner.nextLine();
                throw new InvalidAmountException("Please enter a valid number");
            }
            double input = scanner.nextDouble();
            if (input < min) {
                throw new InvalidAmountException(msg);
            }
            scanner.nextLine();
            return input;

        } catch (InvalidAmountException e) {
            System.out.print(e.getMessage());
            return doubleInput(scanner, min, msg);
        }
    }

}
