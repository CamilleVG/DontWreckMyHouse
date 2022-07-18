package house.domain;

import house.data.GuestFileRepository;
import house.data.GuestRepository;
import house.data.HostRepository;
import house.model.Guest;

public class GuestService {
    private final GuestRepository repository;
    public GuestService(GuestRepository repo) {this.repository = repo; }
    public Guest findByEmail(String guestEmail) {
        if (guestEmail == null) {
            return null;
        }
        return repository.findByEmail(guestEmail);
    }
}
