package server;

public class PhaseOne implements Runnable {

    private boolean busy;
    private ClientPool pool;
    private ClientLink origin;

    PhaseOne(ClientPool pool) {
        this.busy = false;
        this.pool = pool;
        this.origin = pool.getHighestScoringClient();
    }

    PhaseOne(PhaseTwo phase_two_origin) {
        this.origin = phase_two_origin.getOrigin();
    }

    @Override
    public void run() {
        //Set ourselves busy
        synchronized(this) { this.busy = true; }


        //Run the thing
    }

    public boolean isBusy() {
        synchronized (this) { return this.busy; }
    }

    public ClientLink getOrigin() {
        synchronized (this) { return this.origin; }
    }

}

