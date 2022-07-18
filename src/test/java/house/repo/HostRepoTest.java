package house.repo;

import house.data.HostFileRepository;
import house.data.HostRepository;
import house.data.ReservationFileRepository;
import house.data.ReservationRepository;
import house.model.Host;
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

public class HostRepoTest {
    static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/hosts_seed.csv";
    static final String TEST_FILE_PATH = "./dont-wreck-my-house-data/hosts_test.csv";
    final String hostID =  "c0109865-2281-449f-8098-2a87ef35eeb7";
    //c0109865-2281-449f-8098-2a87ef35eeb7,Magson,dmagsonmx@wp.com,(770) 9056907,10558 Lyons Alley,Atlanta,GA,31119,170,212.5
    HostRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        Host host = repository.findByEmail("dmagsonmx@wp.com");
        assertTrue(hostID.equals(host.getId()));
    }
}
