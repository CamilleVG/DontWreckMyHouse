package house.domain;

import house.data.DataException;
import house.model.Host;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HostServiceTest {
    HostService service = new HostService(new HostFileRepositoryDouble());
    final String hostID =  "f4d6c5e4-d207-4ce0-93b3-f1d1b397883c";

    @Test
    void shouldFindByEmail() throws DataException {
        Host host = service.findByEmail("cmactimpany1u@ebay.com");
        assertTrue(host.getId().equals(hostID));
    }
}
