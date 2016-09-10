package pl.polsl.tpdia.models;

/**
 * Currencies supported by bank
 */
public enum Currency {
    PLN("PLN"), EUR("EUR"), USD("USD"), GBP("GBP"), CHF("CHF");

    private String currencyCode;

    Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return currencyCode;
    }
}
