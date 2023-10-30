import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusStop {
    static int waitingRiders = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore busBoardedSemaphore = new Semaphore(0);
    static Semaphore busArrivalSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        BusStop busStop = new BusStop();
        int riderId = 0;
        long timePrevBus = System.currentTimeMillis();
        long timePrevRider = System.currentTimeMillis();
        double meanRider = 2000;
        double meanBus = 10000;
        double waitTimeRider = 0;
        double waitTimeBus = 0;

        waitTimeRider = new Random().nextInt(5) * meanRider;

        waitTimeBus = new Random().nextInt(4) * meanBus;

        while (true) {
            long timeCurr = System.currentTimeMillis();

            if ((timeCurr - timePrevRider) == waitTimeRider) {
                Rider newRider = busStop.new Rider(riderId++);
                new Thread(newRider).start();
                timePrevRider = timeCurr;
                waitTimeRider = new Random().nextInt(5)  * meanRider;
            }

            if ((timeCurr - timePrevBus) == waitTimeBus) {
                Bus newBus = busStop.new Bus();
                new Thread(newBus).start();
                timePrevBus = timeCurr;
                waitTimeBus = new Random().nextInt(4) * meanBus;
            }
        }
    }

    class Rider implements Runnable {
        private int riderId;

        Rider(int index) {
            this.riderId = index;
        }

        @Override
        public void run() {
            try {
                mutex.acquire();
                waitingRiders += 1;
                System.out.println("Rider with rider id " + riderId + " is waiting in the queue");
                mutex.release();

                busArrivalSemaphore.acquire();
                System.out.println("Rider with rider id " + riderId + " boarded the bus.");
                busBoardedSemaphore.release();
            } catch (InterruptedException interruptedException) {}
        }
    }

    class Bus implements Runnable {
        private int ridersToBoard;

        @Override
        public void run() {
            try {
                mutex.acquire();
                System.out.println("Bus has arrived");
                ridersToBoard = Math.min(waitingRiders, 50);

                for (int i = 0; i < ridersToBoard; i++) {
                    busArrivalSemaphore.release();
                    busBoardedSemaphore.acquire();
                }

                waitingRiders = Math.max((waitingRiders - 50), 0);
                mutex.release();

                System.out.println("Bus departed with " + ridersToBoard + " riders");
            } catch (InterruptedException interruptedException) {
            }
        }
    }
}
