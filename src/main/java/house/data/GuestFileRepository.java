package house.data;

import house.model.Guest;
import house.model.Reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;
    public GuestFileRepository(String path) { this.filePath = path; }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return findAll().stream()
                .filter(i -> i.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        if (guest == null) {
            return null;
        }
        List<Guest> all = findAll();

        int nextId = all.stream()
                .mapToInt(Guest::getId)
                .max()
                .orElse(0) + 1;

        guest.setId(nextId);

        all.add(guest);
        writeAll(all);

        return guest;
    }
    private String serialize(Guest guest) {//guest_id,first_name,last_name,email,phone,state
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                guest.getFirst(),
                guest.getLast(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getState()
        );
    }
    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }
    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            for (Guest g : guests) {
                writer.println(serialize(g));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
