public class Decrementor implements Runnable {
    private final int repetitions;

    public Decrementor(int repetitions) {
        this.repetitions = repetitions;
    }

    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            Counter.decrement();
        }
    }
}
