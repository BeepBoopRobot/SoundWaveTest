import java.util.TimerTask;

public class ping extends TimerTask {
    private Emitter e;

    ping(Emitter e) {
        this.e = e;
    }

    public void run() {
        doPing();
    }

    private void doPing() {
        e.ping();
    }
}
