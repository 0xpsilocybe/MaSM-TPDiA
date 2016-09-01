package pl.polsl.tpdia.console;

import pl.polsl.tpdia.dao.AccountHoldersDAO;
import pl.polsl.tpdia.dao.MySQLDatabase;
import pl.polsl.tpdia.models.AccountHolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            MySQLDatabase database = new MySQLDatabase();
            AccountHoldersDAO accountHolders =  database.getAccountHolders();

            Connection connection = MySQLDatabase.getConnection();
            connection.setAutoCommit(false);
            for (int i = 0; i < 10; i++) {
                AccountHolder accountHolder = new AccountHolder();
                accountHolder.setFirstName("Name_" + i);
                accountHolder.setLastName("Surname_" + i);
                accountHolder.setEmail("name.surname@domain.com");
                accountHolder.setBirthDate(new Date(31536000000L));
                accountHolder.setRegistrationDate(new Date(315360000000L));
                accountHolders.insert(connection, accountHolder);
            }
            connection.commit();

            ArrayList<AccountHolder> select = (ArrayList<AccountHolder>) accountHolders.selectAll(connection);
            select.get(0).setFirstName("zmieniono");

            accountHolders.update(connection, select.get(0));
            accountHolders.delete(connection, 2);
            connection.commit();

            System.in.read();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
