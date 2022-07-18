package house.model;
//guest_id,first_name,last_name,email,phone,state
public class Guest {
    private Integer ID;
    private String first;
    private String last;
    private String email;
    private String phone;
    private String state;

    public void setId(int id) {
        this.ID = id;
    }

    public Integer getId() { return this.ID; }
    public String getFirst() { return this.first; }
    public String getLast() { return this.last; }
    public String getEmail() { return this.email; }
    public String getPhone() { return this.phone; }
    public String getState() { return this.state; }

    public void setFirstName(String first) {
        this.first = first;
    }
    public void setLastName(String last) {
        this.last = last;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setState(String state) {
        this.state = state;
    }
}
