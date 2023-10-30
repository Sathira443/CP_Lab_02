import java.util.Random;

public class BusStop {
    public static void main(String[] args) {

        Bus bus = new Bus();

        Thread busThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    System.out.println("Bus arrived at the stop.");
                    bus.depart();
                } catch (InterruptedException e) {
                    // Handle interrupted exception
                }
            }
        });
        busThread.start();

        // Create rider threads
        for (int i = 1; i <= 100; i++) {
            Rider rider = new Rider(bus);
            Thread riderThread = new Thread(rider);
            riderThread.start();
        }
    }
}
