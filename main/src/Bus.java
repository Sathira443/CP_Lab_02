import java.util.concurrent.Semaphore;

public class Bus {
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
