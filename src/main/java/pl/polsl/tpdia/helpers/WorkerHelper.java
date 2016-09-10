package pl.polsl.tpdia.helpers;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.CountDownLatch;

/**
 * Base worker
 */
public abstract class WorkerHelper implements Runnable {
    protected static final Logger logger = LogManager.getLogger(WorkerHelper.class.getName());

    private final String workerName;
    private boolean isStopRequested = false;
    private Thread runningThread;

    public abstract long getDelayBetweenOperations();

    public WorkerHelper(String workerName) {
        this.workerName = workerName;
        logger.trace("Creating " + this.getWorkerName());
    }

    public void run() {
        if(runningThread != null) {
            throw new IllegalStateException("Cannot start work of this instance of WorkerHelper is already running. Stop it first.");
        }
        logger.trace("Running: " + this.getWorkerName());
        runningThread = Thread.currentThread();
        long duration = getDelayBetweenOperations();
        try{
            while(!isStopRequested){
                Thread.sleep(duration);
                doOperation();
            }
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            logger.error("Terminated " + this.getWorkerName() + " thread.", ex);
        }
    }

    protected abstract void doOperation() throws InterruptedException;
    protected String getWorkerName() {
        return this.workerName;
    }

    public void stop() {
        if(runningThread == null) {
            throw new IllegalStateException("Cannot stop work of WorkerHelper worker when it is not running. Run first.");
        }
        isStopRequested = true;
        runningThread.interrupt();
        runningThread = null;
        logger.trace("Stopped " + this.getWorkerName());
    }
}
