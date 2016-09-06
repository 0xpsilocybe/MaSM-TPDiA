package pl.polsl.tpdia.helpers;

import pl.polsl.tpdia.models.Account;
import pl.polsl.tpdia.models.AccountType;
import pl.polsl.tpdia.models.Currency;

import java.math.BigDecimal;
import java.security.SecureRandom;


/**
 * Random account generator
 */
public class AccountGenerator extends Generator<Account> {
    private EnumGenerator<AccountType> accountTypeGenerator;
    private EnumGenerator<Currency> currencyGenerator;

    public AccountGenerator(SecureRandom random) {
        super(random);
        AccountType aType = AccountType.CD;
        Currency currency = Currency.PLN;
        accountTypeGenerator = new EnumGenerator<>(aType, random);
        currencyGenerator = new EnumGenerator<>(currency, random);
    }

    @Override
    public Account generate() {
        double balance = random.nextDouble() * 10000;
        AccountType type = accountTypeGenerator.generate();
        Currency currency = currencyGenerator.generate();
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(balance));
        account.setType(type.toString());
        account.setCurrency(currency.toString());
        return account;
    }
}
