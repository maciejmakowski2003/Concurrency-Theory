public class Destination extends Node {

    public Destination(Buffer inputBuffer) {
        super(inputBuffer, new Buffer());
    }

    @Override
    public void run() {
        while (true) {
            try {
                inputBuffer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
