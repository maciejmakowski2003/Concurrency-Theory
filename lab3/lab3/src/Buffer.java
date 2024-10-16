import java.util.ArrayList;

public class Buffer {
    static final int N = 5;
    ArrayList<Integer> buffer = new ArrayList<>();

    public Buffer() {}

    public synchronized void produce(int i) throws InterruptedException {
        while (buffer.size() == N) wait();
        buffer.add(i);
        System.out.println(Thread.currentThread().getName() + " produced");

        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) wait();
        System.out.println(Thread.currentThread().getName() + " consumed " + buffer.remove(0));

        notifyAll();
    }
}

