package house.domain;

import house.data.DataException;
import house.data.HostRepository;
import house.model.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepositoryDouble implements HostRepository {
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostFileRepositoryDouble() {  //f4d6c5e4-d207-4ce0-93b3-f1d1b397883c,MacTimpany,cmactimpany1u@ebay.com,(203) 7237610,34059 Randy Parkway,Fairfield,CT,06825,476,595
        Host host = new Host();
        host.setId("f4d6c5e4-d207-4ce0-93b3-f1d1b397883c");
        host.setLast("MacTimpany");
        host.setEmail("cmactimpany1u@ebay.com");
        host.setPhone("(203) 7237610");
        host.setAddress("34059 Randy Parkway");
        host.setCity("Fairfield");
        host.setState("CT");
        host.setPostalCode("06825");
        host.setStandardRate(BigDecimal.valueOf(476));
        host.setWeekendRate(BigDecimal.valueOf(595));

        hosts.add(host);
    }

    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findById(String id) {
        for (Host h : hosts) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Host findByEmail(String email) {
        for (Host h : hosts) {
            if (h.getEmail() == email) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Host add(Host host) throws DataException {
        if (host == null) {
            return null;
        }
        host.setId(java.util.UUID.randomUUID().toString());
        hosts.add(host);
        return host;
    }
}
