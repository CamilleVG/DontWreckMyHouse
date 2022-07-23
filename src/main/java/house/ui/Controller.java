package house.ui;

import house.data.DataException;
import house.domain.*;
import house.model.Guest;
import house.model.Host;
import house.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        this.view.displayHeader("Welcome to Don't-Wreck-This-House");

        try {
            this.runAppLoop();
        } catch (DataException var2) {
            this.view.displayException(var2);
        }

        this.view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = this.view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    this.viewReservationHost();
                    break;
                case MAKE_RESERVATION:
                    this.makeReservation();
                    break;
                case EDIT_RESERVATION:
                    this.editReservation();
                    break;
                case CANCEL_RESERVATION:
                    this.cancelReservation();
                    break;
            }
        } while(option != MainMenuOption.EXIT);

    }

    private void cancelReservation() {
        this.view.displayHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        Host host = hostService.findByEmail(view.getHost());
        if (host == null) {
            this.view.displayStatus(false, "Host email not found in database.");
            return;
        }
        List<Reservation>  allFuture = this.reservationService.findAllFuture(host);
        this.view.displayReservations(allFuture);

        int resID;
        if (allFuture.size() == 0) {
            this.view.displayStatus(false, "No reservations with input host or guest to edit.");
            return;
        }
        else if (allFuture.size() > 1){
            resID = view.selectReservationById(allFuture);
        } else {
            resID = allFuture.get(0).getId();
        }

        Reservation r = this.reservationService.findById(host, resID);
        if (this.view.summaryOK(r, MainMenuOption.CANCEL_RESERVATION)){
            Outcome outcome = this.reservationService.delete(r);
            this.view.displayStatus(outcome.isSuccess(), outcome.getErrorMessages());
        } else {
            this.view.displayStatus(false, "Failed to cancel reservation.");
        }

    }

    private void editReservation() {
        this.view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        Host host = hostService.findByEmail(view.getHost());
        if (host == null) {
            this.view.displayStatus(false, "Host email not found in database.");
            return;
        }
        List<Reservation>  allFuture = this.reservationService.findAllFuture(host);
        this.view.displayHeader(host.getLocation());
        this.view.displayReservations(allFuture);

        int resID;
        if (allFuture.size() == 0) {
            this.view.displayStatus(false, "No reservations with input host or guest to edit.");
            return;
        }
        else if (allFuture.size() > 1){
            resID = view.selectReservationById(allFuture);
        } else {
            resID = allFuture.get(0).getId();
        }
        this.view.displayHeader("Editing Reservation " + resID);
        Reservation r = this.reservationService.findById(host, resID);

        view.editDates(r);

        try {
            reservationService.calculateTotal(r);
        }
        catch (Exception e) {  //Try displaying the total if all input is correct.  Otherwise, display null.
        }

        if (this.view.summaryOK(r, MainMenuOption.EDIT_RESERVATION)) {
            Outcome result = this.reservationService.update(r);
            if (result.isSuccess()) {
                this.view.displayStatus(true, "Reservation " + r.getId() + " updated.");
            } else {
                this.view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            this.view.displayStatus(false, "Reservation update cancelled.");
        }
    }

    private void viewReservationHost() {
        this.view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        Host host = hostService.findByEmail(view.getHost());
        if (host == null) {
            this.view.displayStatus(false, "Host email not found in database.");
            return;
        }
        List<Reservation>  all = this.reservationService.findAll(host);
        this.view.displayHeader(host.getLocation());
        this.view.displayReservations(all);
    }

    private void makeReservation() {
        this.view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        Host host = hostService.findByEmail(view.getHost());
        Guest guest = guestService.findByEmail(view.getGuest());
        if (host == null) {
            this.view.displayStatus(false, "Host email not found in database.");
            return;
        }
        if (guest == null) {
            this.view.displayStatus(false, "Guest email not found in database.");
            return;
        }
        List<Reservation>  allFuture = this.reservationService.findAllFuture(host);
        this.view.displayHeader(host.getLocation());
        this.view.displayReservations(allFuture);
        LocalDate start = view.getStart();
        LocalDate end = view.getEnd();
        Reservation res = new Reservation(host, guest, start, end);

        try {
            reservationService.calculateTotal(res);
        }
        catch (Exception e) {  //Try displaying the total if all input is correct.  Otherwise, display null.
        }

        if (this.view.summaryOK(res, MainMenuOption.MAKE_RESERVATION)) {
            Result result = this.reservationService.add(res);
            if (result.isSuccess()) {
                this.view.displayStatus(true, "Reservation " + res.getId() + " created.");
            } else {
                this.view.displayStatus(false, result.getErrorMessages());
            }
        } else {
            this.view.displayStatus(false, "New reservation cancelled.");
        }
    }
}
