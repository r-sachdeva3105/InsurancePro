package entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "claims")
public class Claims {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "policy_id", nullable = false)
    private Integer policyId;

    @Column(name = "broker_id", nullable = false)
    private Integer brokerId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;
    
    @Column(name = "amount")
    private Double amount;



	public Claims() {
        super();
    }

    public Claims(int policyId, int brokerId, int customerId, String description, Double amount) {
        super();
        this.policyId = policyId;
        this.brokerId = brokerId;
        this.description = description;
        this.customerId = customerId;
        this.status = "Pending";
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
  
}
