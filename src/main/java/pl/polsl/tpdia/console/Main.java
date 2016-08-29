package main.java.pl.polsl.tpdia.console;

import main.java.pl.polsl.tpdia.dao.DatabaseAccess;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        DatabaseAccess dao = new DatabaseAccess();
        dao.initializeDatabase();

        Runnable task = () -> {
          String threadName = Thread.currentThread().getName();
            System.out.println("Hi " + threadName);
        };
        task.run();

        Thread thread = new Thread(task);
        thread.start();


        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
    }
}
