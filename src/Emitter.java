public class Emitter {
    private double speed;
    private double freq;
    private int max;
    private int currentlyAt;
    private boolean atEdge = false;
    long a, b;
    public double tieSpeed;

    Emitter(double speed, double freq, int max) {
        this.speed = speed;
        this.freq = freq;
        this.max = max;
    }

    @Override
    public String toString() {
        return "Emitter{" +
                "speed=" + speed +
                ", freq=" + freq +
                '}';
    }

    double getFreq() {
        return freq;
    }

    int update() {
        if (currentlyAt == 0) a = System.currentTimeMillis();
        if (currentlyAt >= max) {
            currentlyAt = 0;
            Main.alW.clear();
            Main.count = 0;
            atEdge = true;
            b = System.currentTimeMillis();
        } else {
            currentlyAt += speed;
            Main.count++;
            atEdge = false;
            // a = System.currentTimeMillis();
        }
        if (atEdge) tieSpeed =(1500/((double)(b-a)/1000));

        return currentlyAt;
    }

    void ping() {
        synchronized (Main.alW) {
            Main.alW.add(new wave(1000, currentlyAt, 20));
        }
    }

    void upSpeed() {
        speed++;
    }

    void downSpeed() {
        speed--;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }
}
