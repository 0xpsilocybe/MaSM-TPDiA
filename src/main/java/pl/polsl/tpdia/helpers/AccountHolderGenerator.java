package pl.polsl.tpdia.helpers;

import pl.polsl.tpdia.helpers.Generator;
import pl.polsl.tpdia.models.AccountHolder;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Random account holder generator
 */
public class AccountHolderGenerator implements Generator<AccountHolder> {
    private Random random = new Random();
    private ArrayList<String> domains;
    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> maleSurnames;
    private ArrayList<String> femaleSurnames;

    public AccountHolderGenerator() {
        this.domains = readFromFile("domains.txt");
        this.maleNames = readFromFile("maleNames.txt");
        this.femaleNames = readFromFile("femaleNames.txt");
        this.maleSurnames = readFromFile("maleSurnames.txt");
        this.femaleSurnames = readFromFile("femaleSurnames.txt");
    }

    private ArrayList<String> readFromFile(String fileName) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream fileStream = classloader.getResourceAsStream(fileName);

            ArrayList<String> collection = new ArrayList<>();
            try (Scanner scanner = new Scanner(fileStream)) {
                scanner.useDelimiter(",");
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
            return createMale();
        } else {
            return createFemale();
        }
    }

    private AccountHolder createMale() {
        AccountHolder male = new AccountHolder();
        int firstNameIndex = random.nextInt(maleNames.size());
        int lastNameIndex = random.nextInt(maleSurnames.size());
        male.setFirstName(maleNames.get(firstNameIndex));
        male.setLastName(maleSurnames.get(lastNameIndex));
        return createBase(male);
    }

    private AccountHolder createFemale() {
        AccountHolder female = new AccountHolder();
        int firstNameIndex = random.nextInt(femaleNames.size());
        int lastNameIndex = random.nextInt(femaleSurnames.size());
        female.setFirstName(femaleNames.get(firstNameIndex));
        female.setLastName(femaleSurnames.get(lastNameIndex));
        return createBase(female);
    }

    private AccountHolder createBase(AccountHolder holder) {
        int domainIndex = random.nextInt(domains.size());
        String email = holder.getFirstName() + "." + holder.getLastName() + "@" + domains.get(domainIndex);
        holder.setEmail(email);
        holder.setBirthDate(getRandomDate(1920, 1998));
        holder.setRegistrationDate(getRandomDate(2000, 2016));
        return holder;
    }

    private Date getRandomDate(int startYear, int endYear) {
        // Get an Epoch value roughly between 1940 and 2000
        // -946771200000L = January 1, 1940
        // Add up to 70 years to it (using modulus on the next long)
        //long miliseconds = -946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

        long startMs = -(1970 - startYear) * 365 * 24 * 60 * 60 * 1000L;
        long diffMs = (endYear - startYear) * 365 * 24 * 60 * 60 * 1000L;
        long miliseconds = startMs + (Math.abs(random.nextLong()) % (diffMs));
        return new Date(miliseconds);
    }
}
