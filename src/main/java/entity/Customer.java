package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "Customer")  // Optional: specify the table name
public class Customer {

    // Declaring fields

    @Id
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;
    
    @Column(name = "broker_id", nullable = false)
    private Integer brokerId; // Changed to Integer for foreign key reference

	// Defining constructors
    public Customer() {
        // Default constructor required by JPA
    }

    public Customer(Integer id, String name, String email, String phone,Integer brokerId ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.brokerId = brokerId;
    }
    
    public Customer(String name, String email, String phone, Integer brokerId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.brokerId = brokerId;
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

}
