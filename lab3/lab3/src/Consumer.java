public class Consumer extends Thread {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        while (true) {
            try {
                buffer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}