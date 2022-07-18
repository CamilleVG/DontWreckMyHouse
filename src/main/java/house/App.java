package house;

import house.data.GuestFileRepository;
import house.data.HostFileRepository;
import house.data.ReservationFileRepository;
import house.domain.GuestService;
import house.domain.HostService;
import house.domain.ReservationService;
import house.ui.ConsoleIO;
import house.ui.Controller;
import house.ui.View;

public class App {
    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        GuestFileRepository guestFileRepository = new GuestFileRepository("dont-wreck-my-house-data/guests.csv");
        HostFileRepository hostFileRepository = new HostFileRepository("dont-wreck-my-house-data/hosts.csv");
        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("dont-wreck-my-house-data/reservations");

        GuestService guestService = new GuestService(guestFileRepository);
        HostService hostService = new HostService(hostFileRepository);
        ReservationService reservationService = new ReservationService(reservationFileRepository, hostFileRepository, guestFileRepository);

        Controller controller = new Controller(guestService, hostService, reservationService, view);
        controller.run();
    }
}
