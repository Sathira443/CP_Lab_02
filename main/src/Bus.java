import java.util.concurrent.Semaphore;

public class Bus {
    private Semaphore busSemaphore;

    public Bus() {
        busSemaphore = new Semaphore(50);
    }

    public void boardBus() throws InterruptedException {
            busSemaphore.acquire();
            System.out.println("Rider boarded the bus.");

    }

    public void depart() {
        System.out.println("Bus departed.");
        busSemaphore.release(50);
    }
}
