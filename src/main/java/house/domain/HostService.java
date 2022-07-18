package house.domain;

import house.data.HostFileRepository;
import house.data.HostRepository;
import house.model.Host;

public class HostService {
    private final HostRepository repository;
    public HostService(HostRepository repo) {this.repository = repo; }

    public Host findByEmail(String hostEmail) {
        if (hostEmail == null) {
            return null;
        }
        return repository.findByEmail(hostEmail);
    }
}
