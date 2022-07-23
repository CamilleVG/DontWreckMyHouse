package house.data;

import house.model.Guest;
import house.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll(String hostID);
    Reservation findById(String hostID, int id);
    Reservation add(Reservation res) throws DataException;
    boolean update(Reservation res) throws DataException;
    boolean delete(Reservation res) throws DataException;
}
