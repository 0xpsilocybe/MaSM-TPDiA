package pl.polsl.tpdia.helpers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Base worker
 * Created by Szymon on 29.08.2016.
 */
public abstract class WorkerHelper implements Runnable {
    protected static final Logger logger = LogManager.getLogger(WorkerHelper.class.getName());

    private boolean isStopRequested = false;
    private Thread runningThread;

    public abstract long getDelayBetweenOperations();

    public void run() {
        if(runningThread != null) {
            throw new IllegalStateException("Cannot start work of this instance of WorkerHelper is already running. Stop it first.");
        }

        runningThread = Thread.currentThread();
        long duration = getDelayBetweenOperations();

        try{
            while(!isStopRequested){
                Thread.sleep(duration);
                doOperation();
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated WorkerHelper's thread.", ex);
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
