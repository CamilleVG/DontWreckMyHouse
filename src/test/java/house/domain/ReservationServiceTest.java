package house.domain;

import house.data.DataException;
import house.model.Guest;
import house.model.Host;
import house.model.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReservationServiceTest {
    ReservationService service = new ReservationService(
            new ReservationFileRepositoryDouble(),
            new HostFileRepositoryDouble(),
            new GuestFileRepositoryDouble());

    HostService hostService = new HostService(new HostFileRepositoryDouble());
    GuestService guestService = new GuestService(new GuestFileRepositoryDouble());

    @Test
    void shouldAdd() {
        Host host = hostService.findByEmail("cmactimpany1u@ebay.com");
        Guest guest = guestService.findByEmail("lgueny3@example.com");
        LocalDate endDate = LocalDate.now().plusDays(1);
        Reservation res = new Reservation(host, guest, LocalDate.now(), endDate);

        Result<Reservation> result = service.add(res);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
    }

    @Test
    void shouldUpdate() {
        Host host = hostService.findByEmail("cmactimpany1u@ebay.com");
        Guest guest = guestService.findByEmail("lgueny3@example.com");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        Reservation res = new Reservation(host, guest, startDate, endDate);

        Result<Reservation> result = service.add(res);
        startDate = LocalDate.now().plusDays(26);
        endDate = LocalDate.now().plusDays(30);
        res.setStartDate(startDate);
        res.setEndDate(endDate);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(1, result.getPayload().getId());
    }
}
