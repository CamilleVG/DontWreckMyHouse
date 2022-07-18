package house.data;

import house.model.Guest;
import house.model.Host;
import house.model.Reservation;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();
    Host findById(String id);
    Host findByEmail(String email);
    Host add(Host host) throws DataException;
}
