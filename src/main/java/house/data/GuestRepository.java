package house.data;

import house.model.Guest;
import house.model.Host;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();
    Guest findById(int id);
    Guest findByEmail(String email);
    Guest add(Guest guest) throws DataException;
}
