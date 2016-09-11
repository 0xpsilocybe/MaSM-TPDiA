package pl.polsl.tpdia.models;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Transaction database model
 */
public class Transaction extends Model implements java.io.Serializable {

    private int accountFromId;
    private int accountToId;
    private BigDecimal amount;
    private Date postingDate;
    private String type;

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
