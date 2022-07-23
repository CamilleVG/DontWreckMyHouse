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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // 1. An ApplicationContext is the DI container.
        // 2. The ClassPathXmlApplicationContext is a specific type of DI container.
        // It requires an XML configuration file to register beans or object instances.
        // Here, we use "dependency-configuration.xml" but the file can be called whatever we like as long
        // as it uses the correct XML schema.
        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-injection.xml");
/*
        ConsoleIO io = container.getBean(ConsoleIO.class);
        View view = container.getBean(View.class);

        GuestFileRepository guestFileRepository = new GuestFileRepository("dont-wreck-my-house-data/guests.csv");
        HostFileRepository hostFileRepository = new HostFileRepository("dont-wreck-my-house-data/hosts.csv");
        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("dont-wreck-my-house-data/reservations");

        GuestService guestService = new GuestService(guestFileRepository);
        HostService hostService = new HostService(hostFileRepository);
        ReservationService reservationService = new ReservationService(reservationFileRepository, hostFileRepository, guestFileRepository);
*/
        Controller controller = container.getBean(Controller.class);  //new Controller(guestService, hostService, reservationService, view);
        controller.run();
    }
}
