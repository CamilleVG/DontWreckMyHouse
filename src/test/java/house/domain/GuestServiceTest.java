package house.domain;

import house.data.DataException;
import house.model.Guest;
import house.model.Host;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestServiceTest {
    GuestService service = new GuestService(new GuestFileRepositoryDouble());

    @Test
    void shouldFindByEmail() throws DataException {
        Guest guest = service.findByEmail("lgueny3@example.com");
        assertTrue(guest.getId() == 1);
    }
}
