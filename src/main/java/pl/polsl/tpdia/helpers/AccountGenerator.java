package pl.polsl.tpdia.helpers;

import pl.polsl.tpdia.models.Account;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Random account generator
 */
public class AccountGenerator implements Generator<Account> {
    private Random random = new Random();

    @Override
    public Account generate() {
        Account account = new Account();
        account.setAccountHolderId(0);
        account.setBalance(BigDecimal.valueOf(0));
        account.setType("ASD");
        account.setCurrency("asd");
        return account;
    }
}
