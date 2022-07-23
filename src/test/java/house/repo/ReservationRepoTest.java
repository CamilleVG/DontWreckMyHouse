package house.repo;

import house.data.DataException;
import house.model.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import house.model.Reservation;
import house.data.ReservationRepository;
import house.data.ReservationFileRepository;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationRepoTest {

    static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/reservations_seed.csv";
    static final String TEST_FILE_PATH = "./dont-wreck-my-house-data/reservations_test/f4d6c5e4-d207-4ce0-93b3-f1d1b397883c.csv";

    static final String TEST_DIR_PATH = "./dont-wreck-my-house-data/reservations_test";
    static final int RES_COUNT = 17;

    final String hostID =  "f4d6c5e4-d207-4ce0-93b3-f1d1b397883c";
    ReservationRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Reservation> reservations = repository.findAll(hostID);
        assertEquals(RES_COUNT, reservations.size());
    }

    @Test
    void shouldAdd() throws DataException { //5,2021-09-17,2021-09-20,764,1666
        Reservation res = new Reservation();
        res.setStartDate(LocalDate.of(2021, 9, 20));
        res.setEndDate(LocalDate.of(2021, 9, 25));
        res.setGuestID(764);
        res.setTotal(BigDecimal.valueOf(1666.0));
        res.host = new Host();
        res.host.setId(hostID);


        res = repository.add(res);

        assertEquals(18, res.getId());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation res = repository.findById(hostID, 13);
        res.setStartDate(LocalDate.of(2022, 9, 20));
        res.setEndDate(LocalDate.of(2022, 9, 25));
        res.setGuestID(764);
        res.setTotal(BigDecimal.valueOf(1666.0));
        res.host = new Host();
        res.host.setId(hostID);

        assertTrue(repository.update(res));
    }

}
