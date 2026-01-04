package exception;

import java.time.LocalDate;

public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
    
    public InvalidDateException(LocalDate date, String reason) {
        super(String.format("Invalid date: %s - %s", date, reason));
    }
    
    public static void validateFutureDate(LocalDate date) throws InvalidDateException {
        if (date == null) {
            throw new InvalidDateException("Date cannot be empty!");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidDateException(date, "Cannot select a past date!");
        }
    }
    
    public static void validateDateRange(LocalDate start, LocalDate end) throws InvalidDateException {
        validateFutureDate(start);
        if (end == null) {
            throw new InvalidDateException("End date cannot be empty!");
        }
        if (end.isBefore(start)) {
            throw new InvalidDateException(end, "End date cannot be before start date!");
        }
        if (end.equals(start)) {
            throw new InvalidDateException(end, "Trip must be at least 1 day!");
        }
    }
}
