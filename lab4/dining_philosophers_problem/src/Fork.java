import java.util.concurrent.Semaphore;

public class Fork {
    public Semaphore mutex = new Semaphore(1);

    public void take(){
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(){
        mutex.release();
    }

    public boolean tryTake() {
        return mutex.tryAcquire();
    }
}
