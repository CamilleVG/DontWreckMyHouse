package house.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO {
        private static final String INVALID_NUMBER = "[INVALID] Enter a valid number.";
        private static final String NUMBER_OUT_OF_RANGE = "[INVALID] Enter a number between %s and %s.";
        private static final String REQUIRED = "[INVALID] Value is required.";
        private static final String INVALID_DATE = "[INVALID] Enter a date in MM/dd/yyyy format.";
        private final Scanner scanner;
        private DateTimeFormatter formatter;

        public ConsoleIO() {
            this.scanner = new Scanner(System.in);
            this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        }

        public void print(String message) {
            System.out.print(message);
        }

        public void println(String message) {
            System.out.println(message);
        }

        public void printf(String format, Object... values) {
            System.out.printf(format, values);
        }

        public String readString(String prompt) {
            this.print(prompt);
            return this.scanner.nextLine();
        }

        public String readRequiredString(String prompt) {
            while(true) {
                String result = this.readString(prompt);
                if (result.contains(",")) {
                    this.println("Input cannot contain a comma.");
                } else {
                    if (!result.isBlank()) {
                        return result;
                    }

                    this.println("[INVALID] Value is required.");
                }
            }
        }

        public double readDouble(String prompt) {
            while(true) {
                try {
                    return Double.parseDouble(this.readRequiredString(prompt));
                } catch (NumberFormatException var3) {
                    this.println("[INVALID] Enter a valid number.");
                }
            }
        }

        public double readDouble(String prompt, double min, double max) {
            while(true) {
                double result = this.readDouble(prompt);
                if (result >= min && result <= max) {
                    return result;
                }

                this.println(String.format("[INVALID] Enter a number between %s and %s.", min, max));
            }
        }

        public int readInt(String prompt) {
            while(true) {
                try {
                    return Integer.parseInt(this.readRequiredString(prompt));
                } catch (NumberFormatException var3) {
                    this.println("[INVALID] Enter a valid number.");
                }
            }
        }

        public int readInt(String prompt, int min, int max) {
            while(true) {
                int result = this.readInt(prompt);
                if (result >= min && result <= max) {
                    return result;
                }

                this.println(String.format("[INVALID] Enter a number between %s and %s.", min, max));
            }
        }

        public boolean readBoolean(String prompt) {
            while(true) {
                String input = this.readRequiredString(prompt).toLowerCase();
                if (input.equals("y")) {
                    return true;
                }

                if (input.equals("n")) {
                    return false;
                }

                this.println("[INVALID] Please enter 'y' or 'n'.");
            }
        }

        public LocalDate readLocalDate(String prompt) {
            while(true) {
                String input = this.readRequiredString(prompt);

                try {
                    return LocalDate.parse(input, this.formatter);
                } catch (DateTimeParseException var4) {
                    this.println("[INVALID] Enter a date in mm/dd/yyyy format.");
                }
            }
        }

        public BigDecimal readBigDecimal(String prompt) {
            while(true) {
                String input = this.readRequiredString(prompt);

                try {
                    return new BigDecimal(input);
                } catch (NumberFormatException var4) {
                    this.println("[INVALID] Enter a valid number.");
                }
            }
        }

        public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
            while(true) {
                BigDecimal result = this.readBigDecimal(prompt);
                if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                    return result;
                }

                this.println(String.format("[INVALID] Enter a number between %s and %s.", min, max));
            }
        }
    }

