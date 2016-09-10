package pl.polsl.tpdia.models;

/**
 * Account types
 */
public enum AccountType {

    Checking("Checking account"),
    Savings("Savings account"),
    CD("Certificate of Deposit"),
    MoneyMarket("Money market account"),
    IRA("IRAs");

    private String type;

    AccountType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
