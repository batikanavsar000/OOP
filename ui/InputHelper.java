package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public static String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }
    
    public static String readStringWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input! Please enter an integer.");
            }
        }
    }
    
    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt + " (" + min + "-" + max + ")");
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("⚠️ Please enter a value between " + min + " and " + max + ".");
        }
    }
    
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine().replace(",", ".").trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input! Please enter a valid number.");
            }
        }
    }
    
    public static double readPositiveDouble(String prompt) {
        while (true) {
            double value = readDouble(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("⚠️ Please enter a positive value.");
        }
    }
    
    public static LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (DD.MM.YYYY): ");
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Invalid date format! Example: 25.12.2024");
            }
        }
    }
    
    public static LocalDate readFutureDate(String prompt) {
        while (true) {
            LocalDate date = readDate(prompt);
            if (!date.isBefore(LocalDate.now())) {
                return date;
            }
            System.out.println("⚠️ You cannot select a past date!");
        }
    }
    
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("E") || input.equals("Y") || input.equals("EVET") || input.equals("YES")) {
                return true;
            }
            if (input.equals("H") || input.equals("N") || input.equals("HAYIR") || input.equals("NO")) {
                return false;
            }
            System.out.println("⚠️ Please enter Y (Yes) or N (No).");
        }
    }
    
    public static void printSeparator() {
        System.out.println("═══════════════════════════════════════════════");
    }
    
    public static void printSubSeparator() {
        System.out.println("───────────────────────────────────────────────");
    }
    
    public static void printHeader(String title) {
        printSeparator();
        System.out.println("       " + title);
        printSeparator();
    }
    
    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
