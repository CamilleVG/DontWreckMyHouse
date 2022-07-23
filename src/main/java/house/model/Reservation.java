package house.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//id,start_date,end_date,guest_id,total
public class Reservation {
    private Integer ID;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer guestID;

    private BigDecimal total;

    public Host host;
    public Guest guest;

    public Reservation() {}
    public Reservation(Host host, Guest guest, LocalDate start, LocalDate end) {
        this.host = host;
        this.guest = guest;
        this.startDate = start;
        this.endDate = end;
    }

    public Integer getId() {
        return this.ID;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Integer getGuestID() {
        return this.guestID;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public void setTotal(BigDecimal total) {this.total = total;}

}
