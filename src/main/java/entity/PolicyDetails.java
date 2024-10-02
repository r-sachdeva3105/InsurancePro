package entity;

public class PolicyDetails {

	// declaring fields
	private String customerId;
	private String policyId;
	private String brokerId;
	private String premiumAmount;

	// defining constructors
	public PolicyDetails(String customerId, String policyId, String brokerId, String premiumAmount) {
		this.policyId = policyId;
		this.brokerId = brokerId;
		this.premiumAmount = premiumAmount;
		this.customerId = customerId;
	}

	// Getters and Setters
	public String getPolicyId() {
		return policyId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

}
