package house.ui;

import house.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {

        this.displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        MainMenuOption[] var3 = MainMenuOption.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            MainMenuOption option = var3[var5];
            if (!option.isHidden()) {
                this.io.printf("%s. %s%n", new Object[]{option.getValue(), option.getMessage()});
            }

            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(this.io.readInt(message, min, max));
    }
    public void displayHeader(String message) {
        this.io.println("");
        this.io.println(message);
        this.io.println("=".repeat(message.length()));
    }
    public void displayException(Exception ex) {
        this.displayHeader("A critical error occurred:");
        this.io.println(ex.getMessage());
    }
    public void displayStatus(boolean success, String message) {
        this.displayStatus(success, List.of(message));
    }
    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("\nHost currently has no reservations listed.");
            return;
        }
        for (Reservation res: reservations) {  //id,start_date,end_date,guest_id,total
            io.printf("ID: %s, Start: %s, End: %s, Guest ID: %s, Total: $%.2f \n",
                    res.getId(),
                    res.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                    res.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                    res.getGuestID().toString(),
                    res.getTotal()
            );
        }
    }

    public int selectReservationById(List<Reservation> all) {
        List<Integer> allID = (List<Integer>) all.stream()
                .map(r -> r.getId())
                .collect(Collectors.toList());
        int i ;
        do {
            i = this.io.readInt("Select a reservation ID: ");
        }
        while (!allID.contains(i));

        return i;
    }

    public boolean summaryOK(Reservation r, MainMenuOption option) {
        this.displayHeader("Summary");
        if (option == MainMenuOption.MAKE_RESERVATION || option == MainMenuOption.EDIT_RESERVATION){
            this.io.println("Start: " + r.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            this.io.println("End: " + r.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            this.io.println(r.getTotal() == null ? "Total: NA" : "Total: $" + r.getTotal());
        }
        if (option == MainMenuOption.CANCEL_RESERVATION) {
            this.io.println("Cancelling Reservation " + r.getId());
        }

        if (this.io.readRequiredString("Is this ok? [y/n]: ").trim().equalsIgnoreCase("y")){
            return true;
        }
        return false;
    }

    public String getGuest() {
        String guest = this.io.readRequiredString("Guest Email: ");
        return guest;
    }

    public String getHost() {
        String host = this.io.readRequiredString("Host Email: ");
        return host;
    }
    public LocalDate getStart() {
        return this.io.readLocalDate("Start (mm/dd/yyyy): ");
    }
    public LocalDate getEnd() {
        return this.io.readLocalDate("End (mm/dd/yyyy): ");
    }

    public void editDates(Reservation r) {
        String formattedDate = r.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate start = this.io.editLocalDate(String.format("Start (%s): ", formattedDate));
        formattedDate = r.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate end = this.io.editLocalDate(String.format("End (%s): ", formattedDate));

        if (start != null) {
            r.setStartDate(start);
        }
        if (end != null) {
            r.setEndDate(start);
        }
    }
}
