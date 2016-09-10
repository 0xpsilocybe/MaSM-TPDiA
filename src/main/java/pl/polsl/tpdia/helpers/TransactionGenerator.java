package pl.polsl.tpdia.helpers;

import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.models.TransactionType;

import java.math.BigDecimal;
import java.security.SecureRandom;

/**
 * Random transaction generator
 */
public class TransactionGenerator extends Generator<Transaction> {
    private EnumGenerator<TransactionType> transactionTypeGenerator;

    public TransactionGenerator (SecureRandom random)  {
        super(random);
        TransactionType tType = TransactionType.ATM;
        transactionTypeGenerator = new EnumGenerator<>(tType, random);
    }

    @Override
    public Transaction generate() {
        double amount = random.nextDouble() * 5000;
        TransactionType type = transactionTypeGenerator.generate();
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setType(type.toString());
        return transaction;
    }

    @Override
    public Transaction getNew() {
        return new Transaction();
    }
}
