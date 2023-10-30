import java.util.Random;

public class Rider implements Runnable {
    private Bus bus;

    public Rider(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(30000) + 20000);
            bus.boardBus();
        } catch (InterruptedException e) {
        }
    }
}
