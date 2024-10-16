public class Incrementor implements Runnable {
    private final int repetitions;

    public Incrementor(int repetitions) {
        this.repetitions = repetitions;
    }

    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            Counter.increment();
        }
    }
}
