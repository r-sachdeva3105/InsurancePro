package entity;


// add new page for claim history
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_history")
public class ClaimsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "claim_id")
    private Integer claim;

    @Column(name = "new_status", nullable = false)
    private String newStatus;

    @Column(name = "updated_by")
    private Integer updatedBy;


    @Column(name = "change_reason")
    private String changeReason;

    public ClaimsHistory() {
        super();
    }

    public ClaimsHistory(Integer claim, String newStatus, Integer updatedBy, String changeReason) {
        this.claim = claim;
        this.newStatus = newStatus;
        this.updatedBy = updatedBy;
        this.changeReason = changeReason;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClaim() {
        return claim;
    }

    public void setClaim(Integer claim) {
        this.claim = claim;
    }

 

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

 

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
}
