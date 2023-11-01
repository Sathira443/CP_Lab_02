import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusStop {
    static int waitingRiders = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore busBoardedSemaphore = new Semaphore(0);
    static Semaphore busArrivalSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        int riderId = 1, busId = 1;
        long timePrevBus = System.currentTimeMillis();
        long timePrevRider = System.currentTimeMillis();

        double meanRider = 250, meanBus = 10000;
        double waitTimeRider, waitTimeBus;
        Random random = new Random();

        waitTimeRider = random.nextInt(5) * meanRider; // 0 - 1000
        waitTimeBus = waitTimeRider + random.nextInt(5) * meanBus; // 0 - 40000

        while (true) {
            long timeCurrent = System.currentTimeMillis();

            if ((timeCurrent - timePrevRider) == waitTimeRider) {
                Rider newRider = new Rider(riderId++);
                new Thread(newRider).start();
                timePrevRider = timeCurrent;
                waitTimeRider = random.nextInt(5)*meanRider;
            }

            if ((timeCurrent - timePrevBus) == waitTimeBus) {
                Bus newBus = new Bus(busId++);
                new Thread(newBus).start();
                timePrevBus = timeCurrent;
                waitTimeBus = waitTimeRider + random.nextInt(5)*meanBus;
            }
//            long timeCurr = System.currentTimeMillis();
//            if ((timeCurr - timePrevRider) == waitTimeRider) {
//
//                Rider newRider = new Rider(riderId++);
//                new Thread(newRider).start();
//                timePrevRider = timeCurr;
//                waitTimeRider = new Random().nextInt(5) * meanRider;
//
//            }
//
//            if ((timeCurr - timePrevBus) == waitTimeBus) {
//                Bus newBus = new Bus(busId++);
//                new Thread(newBus).start();
//                timePrevBus = timeCurr;
//                waitTimeBus = waitTimeRider + new Random().nextInt(4) * meanBus;
//            }
        }
    }

    static class Rider implements Runnable {
        private int riderId;

        public Rider(int riderId) {
            this.riderId = riderId;
        }

        @Override
        public void run() {
            try {
                mutex.acquire();
                waitingRiders += 1;
                System.out.println("Rider with rider id " + riderId + " is waiting in the queue");
                mutex.release();

                busArrivalSemaphore.acquire();
                System.out.println("Rider with rider id " + riderId + " boarded the bus");
                busBoardedSemaphore.release();


            } catch (InterruptedException ignored) {
            }
        }
    }

    static class Bus implements Runnable {
        private int ridersToBoard;
        private int busId;

        public Bus(int busId) {
            this.busId = busId;
        }

        @Override
        public void run() {
            try {
                mutex.acquire();
                System.out.println("Bus id with " + busId + " has arrived");
                ridersToBoard = Math.min(waitingRiders, 50);
                mutex.release();

                for (int i = 0; i < ridersToBoard; i++) {
                    busArrivalSemaphore.release();
                    busBoardedSemaphore.acquire();
                }

                mutex.acquire();
                waitingRiders = Math.max((waitingRiders - 50), 0);
                mutex.release();

                System.out.println("Bus id with " + busId + " departed with " + ridersToBoard + " riders on board.");
            } catch (InterruptedException ignored) {
            }
        }
    }
}
