package house.model;

import java.math.BigDecimal;

//id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
public class Host {
    private String ID;
    private String last;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    public Host() {}

    public String getLocation() {
        return String.format("%s, %s, %s, %s", address, city, state, postal_code);
    }

    public void setId(String id) { this.ID = id;}
    public void setLast(String last) { this.last = last;}
    public void setEmail(String email) { this.email = email;}
    public void setPhone(String phone) { this.phone = phone;}
    public void setAddress(String address) { this.address = address;}
    public void setCity(String city) { this.city = city;}
    public void setState(String state) { this.state = state;}
    public void setPostalCode(String postalCode) { this.postal_code = postalCode;}
    public void setStandardRate(BigDecimal standardRate) { this.standardRate = standardRate;}
    public void setWeekendRate(BigDecimal weekendRate) { this.weekendRate = weekendRate;}
    public String getId() {return this.ID;}
    public String getLast() {return this.last;}

    public String getEmail() { return this.email;}
    public String getPhone() { return this.phone;}
    public String getAddress() { return this.address;}
    public String getCity() { return this.city;}
    public String getState() { return this.state;}
    public String getPostalCode() { return this.postal_code;}
    public BigDecimal getStandardRate() { return this.standardRate;}
    public BigDecimal getWeekendRate() { return this.weekendRate;}
}
