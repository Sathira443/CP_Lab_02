import java.util.Random;

public class Rider implements Runnable {
    private Bus bus;

    public Rider(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(30000) + 20000); // Random arrival time between 20 and 50 seconds
            bus.boardBus();
        } catch (InterruptedException e) {
            // Handle interrupted exception
        }
    }
}