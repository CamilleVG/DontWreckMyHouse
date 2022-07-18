package house.domain;

import house.data.DataException;
import house.data.ReservationRepository;
import house.model.Host;
import house.model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepositoryDouble implements ReservationRepository {

    final String hostID =  "f4d6c5e4-d207-4ce0-93b3-f1d1b397883c";

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationFileRepositoryDouble() {
        Reservation res = new Reservation();
        res.setGuestID(4);  //Reservation: 4, 2021-04-13,2021-04-16,308,1428.0
        res.setStartDate(LocalDate.of(2021,4,13));
        res.setEndDate(LocalDate.of(2021,4,16));
        res.host = new Host();
        res.host.setId(hostID);

        reservations.add(res);
    }


    @Override
    public Reservation findById(String hostID, int id) {
        for(Reservation res: reservations){
            if (res.getId() == id){
                return res;
            }
        }
        return null;
    }

    @Override
    public List<Reservation> findAll(String hostID) {
        return reservations;
    }

    @Override
    public Reservation add(Reservation res) throws DataException {
        res.setId(reservations.size());
        reservations.add(res);
        return res;
    }

    @Override
    public boolean update(Reservation res) throws DataException {
        for (Reservation r : reservations){
            if (r.getId() == res.getId()) {
                r = res;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation res) throws DataException {
        return false;
    }
}
