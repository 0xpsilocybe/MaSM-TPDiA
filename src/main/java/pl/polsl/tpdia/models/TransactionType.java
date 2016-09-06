package pl.polsl.tpdia.models;

/**
 * Transaction types
 * @author Psilo
 */
public enum TransactionType {
    ATM("ATM"), /** Deposit or withdraw funds using an ATM */
    Charge("Charge"), /** Record a purchase on a credit card or withdraw funds using a debit card */
    Check("Check"), /** Withdraw funds by writing a paper check */
    Deposit("Deposit"), /** Add funds to an account by any method */
    Online("Online"), /** Withdraw funds through a web-based store or online banking service */
    POS("POS"), /** Withdraw funds through a point-of-sale transaction */
    Transfer("Transfer"), /** Move funds from one account to another */
    Withdrawal("Withdrawal"); /** Deduct funds from an account by any method */

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
