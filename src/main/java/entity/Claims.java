package entity;


import jakarta.persistence.*;

@Entity
@Table(name = "claims")
public class Claims {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String policyId;
    private String brokerId;
    private String status; // Approved, Pending, Rejected
    private String description;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPolicyId() { return policyId; }
    public void setPolicyId(String policyId) { this.policyId = policyId; }
    public String getBrokerId() { return brokerId; }
    public void setBrokerId(String brokerId) { this.brokerId = brokerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

