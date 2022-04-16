package Raghav.EcommerceProject.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User {

    @Id
    private int id;
    @Column(unique=true ,nullable = false)
    private String email;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String middle_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String password;
    private boolean deleted;
    private boolean active;
    private boolean expired;
    private boolean locked;
    private int invalid_attempt_count;
    private String password_update_date;




    @OneToOne(mappedBy = "user")
    private Address address;

    @OneToOne(mappedBy = "user")
    private Seller seller;

    @OneToOne(mappedBy = "user")
    private Customer customer;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id") )
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getInvalid_attempt_count() {
        return invalid_attempt_count;
    }

    public void setInvalid_attempt_count(int invalid_attempt_count) {
        this.invalid_attempt_count = invalid_attempt_count;
    }

    public String getPassword_update_date() {
        return password_update_date;
    }

    public void setPassword_update_date(Date password_update_date) {
        this.password_update_date = String.valueOf(password_update_date);
    }







    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}