package pl.polsl.tpdia.models;

import java.math.BigDecimal;

/**
 * Account database model
 */
public class Account extends Model implements java.io.Serializable {

    private int accountHolderId;
    private BigDecimal balance;
    private String currency;
    private String type;

    public int getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(int accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
