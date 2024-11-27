package entity;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "policy_details")
public class PolicyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; // Auto-generated ID for policy details

    @Column(name = "policy_id", nullable = false)
    private Integer policyId; // Foreign key reference to policies table

    @Column(name = "customer_id", nullable = false)
    private Integer customerId; // Foreign key reference to customer table

    @Column(name = "broker_id", nullable = false)
    private Integer brokerId; // Foreign key reference to brokers table

    @Column(name = "premium_amount")
    private Double premiumAmount; // Premium amount for the policy

    @Column(name = "start_date", nullable = false)
    private Date startDate; // Policy start date

    @Column(name = "end_date", nullable = false)
    private Date endDate; // Policy end date

    @Column(name = "status", nullable = false)
    private String status; // Policy status (Active, Renewed, Cancelled)
    
    @Column(name = "term_length", nullable = false)
    private String termLength;

    @Column(name = "term_factor")
    private Double termFactor;

    // Default constructor needed by Hibernate
    public PolicyDetails() {}

    // Constructor with parameters
    public PolicyDetails(Integer policyId, Integer customerId, Integer brokerId, Double premiumAmount, Date startDate, Date endDate, String status,  String termLength) {
        this.policyId = policyId;
        this.customerId = customerId;
        this.brokerId = brokerId;
        this.premiumAmount = premiumAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termLength = termLength;
        this.calculateTermFactor(termLength);
        this.termFactor = this.getTermFactor();
        
    }
    public PolicyDetails(Integer policyId, Integer customerId, Integer brokerId, Double premiumAmount, Date startDate, Date endDate, String status,  String termLength, Double termFactor) {
        this.policyId = policyId;
        this.customerId = customerId;
        this.brokerId = brokerId;
        this.premiumAmount = premiumAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termLength = termLength;
        this.termFactor = termFactor;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTermLength() {
		return termLength;
	}

	public void setTermLength(String termLength) {
		this.termLength = termLength;
	}

	public Double getTermFactor() {
		return termFactor;
	}
	public void setTermFactor(Double termFactor) {
		this.termFactor = termFactor;
	}


	public Double calculateTermFactor(String termLength) {
		if (termLength.equals("1 year"))
			return 1.0;
		else if(termLength.equals("3 years"))
			return 1.1;
		else if(termLength.equals("5 years"))
			return 1.2;
		else if(termLength.equals("8 years"))
			return 1.38;
		else if(termLength.equals("10 years"))
			return 1.5;
		return 0.0;
		
	}
}
