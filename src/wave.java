public class wave {
    private double radius;
    private int speed;
    private int startX;

    public double getRadius() {
        return radius;
    }

    void setRadius(double radius) {
        this.radius = radius;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    wave(int speed, int startX, double radius) {
        this.speed = speed;
        this.startX = startX;
        this.radius = radius;
    }

    void update() {
        radius += speed / 100;
    }


}
