package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "type", nullable = false, length = 255)
    private String type;

    @Column(name = "coverage_amount", precision = 10, scale = 2)
    private Double coverageAmount;

    @Column(name = "premium", precision = 10, scale = 2)
    private Double premium;


    // Default constructor for JPA
    public Policy() {}

    // Constructor with parameters (without id, createdAt, and updatedAt for insertion)
    public Policy(String name, String description, String type, Double coverageAmount, Double premium) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
    }

    // Full constructor (for scenarios where all fields are required)
    public Policy(Integer id, String name, String description, String type, Double coverageAmount, Double premium) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
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

    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    
}
