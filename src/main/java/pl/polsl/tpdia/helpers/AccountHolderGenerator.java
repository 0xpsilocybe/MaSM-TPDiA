package pl.polsl.tpdia.helpers;

import pl.polsl.tpdia.models.AccountHolder;

import java.io.*;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Random account holder generator
 */
public class AccountHolderGenerator extends Generator<AccountHolder> {
    private ArrayList<String> domains;
    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> maleSurnames;
    private ArrayList<String> femaleSurnames;

    public AccountHolderGenerator(SecureRandom random) {
        super(random);
        this.domains = readFromFile("domains.txt");
        this.maleNames = readFromFile("maleNames.txt");
        this.femaleNames = readFromFile("femaleNames.txt");
        this.maleSurnames = readFromFile("maleSurnames.txt");
        this.femaleSurnames = readFromFile("femaleSurnames.txt");
    }

    private ArrayList<String> readFromFile(String fileName) {
        try {
            InputStream fileStream = this.getClass().getResourceAsStream(fileName);
            ArrayList<String> collection = new ArrayList<>();
            try (Scanner scanner = new Scanner(fileStream)) {
                scanner.useDelimiter("\r\n");
                while (scanner.hasNext()) {
                    collection.add(scanner.next());
                }
            }
            return collection;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AccountHolder generate() {
        boolean letThereBeMan = random.nextBoolean();
        if (letThereBeMan) {
            return create(maleNames, maleSurnames);
        } else {
            return create(femaleNames, femaleSurnames);
        }
    }

    private AccountHolder create(ArrayList<String> firstNames, ArrayList<String> lastNames) {
        AccountHolder accountHolder = new AccountHolder();
        int firstNameIndex = random.nextInt(firstNames.size());
        String firstName = firstNames.get(firstNameIndex);
        int lastNameIndex = random.nextInt(lastNames.size());
        String lastName = lastNames.get(lastNameIndex);
        int domainIndex = random.nextInt(domains.size());
        String domain = domains.get(domainIndex);
        String email = firstName + "." + lastName + "@" + domain;
        Date birthDate = getRandomDate(1920, 1998);
        Date registrationDate = getRandomDate(2000, 2016);
        accountHolder.setFirstName(firstName);
        accountHolder.setLastName(lastName);
        accountHolder.setEmail(email);
        accountHolder.setBirthDate(birthDate);
        accountHolder.setRegistrationDate(registrationDate);
        return accountHolder;
    }

    private Date getRandomDate(int startYear, int endYear) {
        long startMs = -(1970 - startYear) * 365 * 24 * 60 * 60 * 1000L;
        long diffMs = (endYear - startYear) * 365 * 24 * 60 * 60 * 1000L;
        long milliseconds = startMs + (Math.abs(random.nextLong()) % (diffMs));
        return new Date(milliseconds);
    }
}
