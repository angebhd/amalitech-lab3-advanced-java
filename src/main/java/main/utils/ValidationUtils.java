package main.utils;

import main.models.exceptions.InvalidAmountException;

import java.util.Scanner;
import java.util.regex.Pattern;

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


  public String validatePhoneNumber(Scanner scanner) {
    Pattern phoneNumberPattern = Pattern.compile("^\\+\\d+\\s\\d{9}$");
    String input = scanner.nextLine().trim();
    if (phoneNumberPattern.matcher(input).matches()) {
      return input;
    }
    System.out.print("Please enter a valid phone number, eg: +250 790123456: ");
    return validatePhoneNumber(scanner);
  }

  public String validateAccountNumber(Scanner scanner) {
    Pattern accounNumberPattern = Pattern.compile("^ACC\\d{3}$");
    String input = scanner.nextLine().trim().toUpperCase();
    if (accounNumberPattern.matcher(input).matches()) {
      return input;
    }
    System.out.print("Please enter a valid account number: ");
    return validatePhoneNumber(scanner);
  }
}
