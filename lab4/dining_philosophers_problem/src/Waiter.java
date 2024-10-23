import java.util.concurrent.Semaphore;

public class Waiter {
    private final Semaphore mutex;

    Waiter(int numPhilosophers) {
        mutex = new Semaphore(numPhilosophers - 1);
    }

    public void ask() {
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        try {
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
