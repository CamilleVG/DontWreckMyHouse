package house.domain;

import house.data.DataException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class HostServiceTest {
    HostService service = new HostService(new HostFileRepositoryDouble());

    @Test
    void shouldNotSaveNullName() throws DataException {
        /*
        Item item = new Item(0, null, Category.EDIBLE, new BigDecimal("5.00"));
        Result<Item> result = service.add(item);
        assertFalse(result.isSuccess());

         */
    }
}
