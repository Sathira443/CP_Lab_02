import java.util.concurrent.Semaphore;

public class Bus {
    private Semaphore busSemaphore;

    public Bus() {
        busSemaphore = new Semaphore(50);
    }

    public void boardBus() {
        try {
            busSemaphore.acquire();
            System.out.println("Rider boarded the bus.");
        } catch (InterruptedException e) {
        }
    }

    public void depart() {
        System.out.println("Bus departed.");
        busSemaphore.release(50);
    }
}