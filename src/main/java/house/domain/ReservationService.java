package house.domain;

import house.data.*;
import house.model.Host;
import house.model.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final GuestRepository guestRepo;
    private final HostRepository hostRepo;
    private final ReservationRepository resRepo;


    public ReservationService(ReservationRepository reservationRepo, HostRepository hostRepo, GuestRepository guestRepo) {
        this.guestRepo = guestRepo;
        this.hostRepo = hostRepo;
        this.resRepo = reservationRepo;
    }

    public List<Reservation> findAll(Host host) {
        host = hostRepo.findByEmail(host.getEmail());
        if (host == null){
            return new ArrayList<Reservation>();
        }
        return resRepo.findAll(host.getId()).stream()
                .sorted((a, b) -> a.getStartDate().compareTo(b.getStartDate()))
                .collect(Collectors.toList());
    }
    public List<Reservation> findAllFuture(Host host) {
        host = hostRepo.findByEmail(host.getEmail());
        if (host == null){
            return new ArrayList<Reservation>();
        }
        return resRepo.findAll(host.getId()).stream()
                .filter(r -> (LocalDate.now().equals(r.getStartDate())|| LocalDate.now().isBefore(r.getStartDate())))
                .sorted((a, b) -> a.getStartDate().compareTo(b.getStartDate()))
                .collect(Collectors.toList());
    }

    public Reservation findById(Host host, int resID) {
        if (host == null) {
            return null;
        }
        Reservation res = resRepo.findById(host.getId(), resID);
        res.host = host;
        res.guest = guestRepo.findById(res.getGuestID());
        return res;
    }

    public Result add(Reservation reservation) {
        Result<Reservation> result = (Result<Reservation>) validate(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        try {
            result.setPayload(resRepo.add(reservation));
        }
        catch (DataException e){
            result.addErrorMessage(e.getMessage());
        }
        return result;
    }

    public Outcome update(Reservation reservation) {
        Result<Reservation> result = (Result<Reservation>) validate(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        try {
            if (!resRepo.update(reservation)){
                result.addErrorMessage("Failed to update reservation.");
            }
            result.setPayload(reservation);
        }
        catch (DataException e){
            result.addErrorMessage(e.getMessage());
        }
        return result;
    }

    public Outcome delete(Reservation reservation) {
        Outcome outcome = new Outcome();
        try {
            if (!resRepo.delete(reservation)){
                outcome.addErrorMessage("Failed to delete reservation.");
            }
        }
        catch (DataException e){
            outcome.addErrorMessage(e.getMessage());
        }
        return outcome;

    }
    private Outcome validate(Reservation reservation) {

        Result<Reservation> result = new Result();

        validateParties(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateDates(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateNoDuplicates(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

    private void validateParties(Reservation reservation, Outcome result){
        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return;
        }
        if (reservation.host == null) {
            result.addErrorMessage("Host for reservation is required.");
            return;
        }
        if (reservation.host.getId() == null  || hostRepo.findById(reservation.host.getId()) == null){
            result.addErrorMessage("Host must have an Id designated from repository.");
            return;
        }
        if (reservation.guest == null) {
            result.addErrorMessage("Guest for reservation is required.");
            return;
        }
        if (reservation.guest.getId() == null || guestRepo.findById(reservation.guest.getId()) == null){
            result.addErrorMessage("Guest must have an Id designated from repository.");
            return;
        } else {
            reservation.setGuestID(reservation.guest.getId());
        }
    }

    private void validateDates(Reservation reservation, Result<Reservation> result){

        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Reservation start date is required.");
            return;
        }
        if (reservation.getEndDate() == null) {
            result.addErrorMessage("Reservation end date is required.");
            return;
        }
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation start date has passed.  New reservations must be for the future.");
        }
        if (!reservation.getStartDate().isBefore(reservation.getEndDate())) {
            result.addErrorMessage("Reservation start date must be at least one day before end date.");
            return;
        }

        calculateTotal(reservation); //sets the total field of reservation object

        List<LocalDate> takenDates = new ArrayList<>();

        for (Reservation res : this.findAllFuture(reservation.host)) {
            if (reservation.getId() != null) {  //if the reservation is already in the repo and being edited
                if (res.getId() == reservation.getId()) {
                    continue;
                }
            }
            takenDates.addAll(datesList(res));
        }

        List<LocalDate> reservationDates = datesList(reservation);

        for (int i = 0; i <= reservationDates.size() - 2; i++){
            if (takenDates.contains(reservationDates.get(i))) {
                result.addErrorMessage("Reservation dates conflict.");
                break;
            }
        }
    }

    private void validateNoDuplicates(Reservation reservation, Result<Reservation> result) {
        List<Reservation> reservations = resRepo.findAll(reservation.host.getId()).stream()
                .filter(r -> (r.getId() != reservation.getId() && r.getGuestID() == reservation.getGuestID() && r.getStartDate().equals(reservation.getStartDate()) && r.getStartDate().equals(reservation.getStartDate())))
                .collect(Collectors.toList());  //date, Item, and Forager
        if (!reservations.isEmpty()) {
            result.addErrorMessage("Duplicate found.  Reservation already exists.");
        }
    }
    private List<LocalDate> datesList(Reservation res) {
        List<LocalDate> totalDates = new ArrayList<>();
        LocalDate startDate = LocalDate.of(res.getStartDate().getYear(), res.getStartDate().getMonth(), res.getStartDate().getDayOfMonth());
        while (!startDate.isAfter(res.getEndDate())) {
            totalDates.add(startDate);
            startDate = startDate.plusDays(1);
        }
        return totalDates;
    }
    public void calculateTotal(Reservation res){
        BigDecimal total = new BigDecimal(0);
        List<LocalDate> totalDates = datesList(res);
        for (int i = 0; i <= totalDates.size() - 2; i++) {
            if (totalDates.get(i).getDayOfWeek().equals(DayOfWeek.FRIDAY) || totalDates.get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                total = total.add(res.host.getWeekendRate());
            }else {
                total = total.add(res.host.getStandardRate());
            }
        }
        res.setTotal(total);
    }
}
