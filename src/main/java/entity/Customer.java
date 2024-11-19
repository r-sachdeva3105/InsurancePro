package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "customer")  // Specify the table name (updated to lowercase)
public class Customer {

    // Declaring fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "broker_id", nullable = false)
    private Integer brokerId;

    @Column(name = "address")
    private String address;

    @Column(name = "dob") // Date of birth
    private String dob; // Use String or LocalDate depending on your database setup



    // Defining constructors
    public Customer() {
        // Default constructor required by JPA
    }

    public Customer(Integer id, String name, String email, String phone, Integer brokerId, String address, String dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.brokerId = brokerId;
        this.address = address;
        this.dob = dob;
    }

    public Customer(String name, String email, String phone, Integer brokerId, String address, String dob) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.brokerId = brokerId;
        this.address = address;
        this.dob = dob;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
