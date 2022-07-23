package house.repo;

import house.data.GuestFileRepository;
import house.data.GuestRepository;
import house.data.ReservationFileRepository;
import house.data.ReservationRepository;
import house.model.Guest;
import house.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestRepoTest {
    static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/guests_seed.csv";
    static final String TEST_FILE_PATH = "./dont-wreck-my-house-data/guests_test.csv";
    final int guestID = 962;
    //962,Duff,Wigelsworth,dwigelsworthqp@chron.com,(707) 9392328,CA
    GuestRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        Guest guest = repository.findById(guestID);
        assertTrue(guest.getEmail().equals("dwigelsworthqp@chron.com"));
    }
}
