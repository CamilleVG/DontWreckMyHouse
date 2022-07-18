package house.data;

import house.model.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;
    public ReservationFileRepository(String path)  { this.directory = path;}

    private String getFilePath(String hostID) {
        return Paths.get(directory, hostID + ".csv").toString();
    }

    @Override
    public Reservation findById(String hostID, int id) {
        if (hostID == null) {
            return null;
        }
        return findAll(hostID).stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reservation> findAll(String hostID) {

        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostID)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Reservation add(Reservation res) throws DataException {
        if (res == null) {
            return null;
        }

        List<Reservation> all = findAll(res.host.getId());

        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        res.setId(nextId);

        all.add(res);
        writeAll(all, res.host.getId());

        return res;
    }

    @Override
    public boolean update(Reservation res) throws DataException {
        if (res == null) {
            return false;
        }

        List<Reservation> all = findAll(res.host.getId());
        for (int i = 0; i < all.size(); i++) {
            if (res.getId() == all.get(i).getId()) {
                all.set(i, res);
                writeAll(all, res.host.getId());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Reservation res) throws DataException {
        if (res == null) {
            return false;
        }

        List<Reservation> all = findAll(res.host.getId());
        for (int i = 0; i < all.size(); i++) {
            if (res.getId() == all.get(i).getId()) {
                all.remove(i);
                writeAll(all, res.host.getId());
                return true;
            }
        }

        return false;
    }

    private String serialize(Reservation res) {
        return String.format("%s,%s,%s,%s,%s",
                res.getId(),
                res.getStartDate().toString(),
                res.getEndDate().toString(),
                res.getGuestID(),
                res.getTotal());
    }

    private Reservation deserialize(String[] fields) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setGuestID(Integer.parseInt(fields[3]));
        result.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));
        return result;
    }

    protected void writeAll(List<Reservation> reservations, String hostID) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostID))) {

            writer.println(HEADER);

            if (reservations == null) {
                return;
            }

            for (Reservation res : reservations) {
                writer.println(serialize(res));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
