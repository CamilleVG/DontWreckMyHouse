package house.domain;

import house.data.DataException;
import house.data.GuestRepository;
import house.model.Guest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepositoryDouble implements GuestRepository {
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestFileRepositoryDouble() { //4,Leonidas,Gueny,lgueny3@example.com,(412) 6493981,PA
        Guest guest = new Guest();
        guest.setId(1);
        guest.setFirstName("Leonidas");
        guest.setLastName("Gueny");
        guest.setEmail("lgueny3@example.com");
        guest.setPhone("(412) 6493981");
        guest.setState("PA");
        guests.add(guest);
        guest.setId(2);
        guest.setEmail("leoroar3@gmail.com");
        guests.add(guest);
        guest.setId(3);
        guest.setEmail("topgun3@example.com");
        guests.add(guest);
        guest.setId(4);
        guest.setEmail("luckygrinch@yahoo.com");
        guests.add(guest);

    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findById(int id) {
        for(Guest g : guests) {
            if (g.getId() == id){
                return g;
            }
        }
        return null;
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        if (guest == null) {
            return null;
        }
        guest.setId(guests.size());
        guests.add(guest);
        return guest;
    }
}
