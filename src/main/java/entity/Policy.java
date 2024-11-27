package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "base_rate")
    private Double baseRate;
    
    @Column(name = "coverage_amount")
    private Double coverageAmount;
    

    


    

	// Default constructor for JPA
    public Policy() {}

    // Constructor with parameters (without id, createdAt, and updatedAt for insertion)
    public Policy(String name, String description, String type, Double baseRate, Double coverageAmount) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.baseRate = baseRate;
        this.coverageAmount = coverageAmount;
    }

    // Full constructor (for scenarios where all fields are required)
    public Policy(Integer id, String name, String description, String type, Double baseRate, Double coverageAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.baseRate = baseRate;
        this.coverageAmount = coverageAmount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }


    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }


  

    
}
