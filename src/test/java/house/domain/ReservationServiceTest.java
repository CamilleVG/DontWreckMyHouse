package house.domain;

import house.data.DataException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReservationServiceTest {
    ReservationService service = new ReservationService(
            new ReservationFileRepositoryDouble(),
            new HostFileRepositoryDouble(),
            new GuestFileRepositoryDouble());

    @Test
    void shouldAdd() throws DataException {
/*
        Forage forage = new Forage();
        forage.setDate(LocalDate.now());
        forage.setForager(ForagerRepositoryDouble.FORAGER);
        forage.setItem(ItemRepositoryDouble.ITEM);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(36, result.getPayload().getId().length());

 */
    }

    @Test
    void shouldNotAddWhenForagerNotFound() throws DataException {
        /*

        Forager forager = new Forager();
        forager.setId("30816379-188d-4552-913f-9a48405e8c08");
        forager.setFirstName("Ermengarde");
        forager.setLastName("Sansom");
        forager.setState("NM");

        Forage forage = new Forage();
        forage.setDate(LocalDate.now());
        forage.setForager(forager);
        forage.setItem(ItemRepositoryDouble.ITEM);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertFalse(result.isSuccess());

         */
    }
}
