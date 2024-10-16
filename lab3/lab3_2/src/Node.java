public class Node extends Thread {
    protected final Buffer inputBuffer;
    protected final Buffer outputBuffer;

    public Node(Buffer inputBuffer, Buffer outputBuffer) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
    }

    public void run() {
        while(true) {
            try {
                int value = inputBuffer.consume();
                outputBuffer.produce(value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
