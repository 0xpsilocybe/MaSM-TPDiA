package pl.polsl.tpdia.helpers;

import java.time.Duration;

/**
 * Created by Szymon on 29.08.2016.
 */
public abstract class WorkerHelper implements Runnable {

    protected final Logger logger;
    private boolean isStopRequested = false;
    private Thread runningThread;

    public abstract long getDelayBetweenOperations();

    public WorkerHelper(Logger logger) {

        this.logger = logger;
    }

    public void run() {

        if(runningThread != null) {
            throw new IllegalStateException("Cannot start work of this instance of WorkerHelper is already running. Stop it first.");
        }

        runningThread = Thread.currentThread();
        long duration = getDelayBetweenOperations();

        try{
            while(!isStopRequested){
                Thread.sleep(getDelayBetweenOperations());
                doOperation();
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.Info("Terminated WorkerHelper's thread." + ex.getMessage());
        }
    }

    protected abstract void doOperation() throws InterruptedException;

    public void stop() {

        if(runningThread == null) {
            throw new IllegalStateException("Cannot stop work of WorkerHelper worker when it is not running. Run first.");
        }

        isStopRequested = true;
        runningThread.interrupt();
        runningThread = null;
    }
}
