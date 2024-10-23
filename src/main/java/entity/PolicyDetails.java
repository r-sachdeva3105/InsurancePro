package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_details")
public class PolicyDetails {

    // Declaring fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Auto-generated ID for policy details

    @Column(name = "policy_id", nullable = false)
    private Integer policyId; // Changed to Integer for foreign key reference

    @Column(name = "customer_id", nullable = false)
    private Integer customerId; // Changed to Integer for foreign key reference

    @Column(name = "broker_id", nullable = false)
    private Integer brokerId; // Changed to Integer for foreign key reference

    @Column(name = "premium_amount", nullable = false)
    private String premiumAmount;

    // Default constructor needed by Hibernate
    public PolicyDetails() {}

    // Defining constructors
    public PolicyDetails(Integer customerId, Integer policyId, Integer brokerId, String premiumAmount) {
        this.policyId = policyId;
        this.brokerId = brokerId;
        this.premiumAmount = premiumAmount;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Integer getId() {
        return id; // Get auto-generated ID
    }

    public void setId(Integer id) {
        this.id = id; // Set auto-generated ID
    }

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public String getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(String premiumAmount) {
        this.premiumAmount = premiumAmount;
    }
}
