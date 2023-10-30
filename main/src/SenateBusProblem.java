import java.util.Random;
import java.util.concurrent.Semaphore;

public class SenateBusProblem {
    public static void main(String[] args) {
        Bus bus = new Bus();
        Thread busThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Bus arrived at the stop.");
                    Thread.sleep(new Random().nextInt(30000) + 20000);
                    bus.depart();
                } catch (InterruptedException e) {
                    // Handle interrupted exception
                }
            }
        });
        busThread.start();

        // Create rider threads
        for (int i = 1; i <= 100; i++) { // Create 100 riders as an example
            Rider rider = new Rider(bus);
            Thread riderThread = new Thread(rider);
            riderThread.start();
        }
    }

    static class Bus {
        private Semaphore busSemaphore;
        private int ridersOnBus;

        public Bus() {
            busSemaphore = new Semaphore(50);
            ridersOnBus = 0;
        }

        public void boardBus() {
            try {
                busSemaphore.acquire();
                ridersOnBus++;
                System.out.println("Rider boarded the bus. Riders on bus: " + ridersOnBus);
            } catch (InterruptedException e) {
                // Handle interrupted exception
            }
        }

        public void depart() {
            System.out.println("Bus departed with " + ridersOnBus + " riders.");
            ridersOnBus = 0; // Reset the count
            busSemaphore.release(50);
        }
    }

    static class Rider implements Runnable {
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

}
