public class Source extends Node {

    public Source(Buffer outputBuffer) {
        super(new Buffer(), outputBuffer);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                outputBuffer.produce(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
