public class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {
                buffer.produce(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}