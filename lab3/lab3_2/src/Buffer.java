import java.util.ArrayList;

public class Buffer {
    static final int N = 1;
    private final ArrayList<Integer> buffer = new ArrayList<>();

    public Buffer() {}

    public synchronized void produce(int i) throws InterruptedException {
        while (buffer.size() == N) wait();
        buffer.add(i);
        System.out.println(Thread.currentThread().getName() + " produced " + i);

        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) wait();
        System.out.println(Thread.currentThread().getName() + " consumed " + buffer.get(0));

        notifyAll();

        return buffer.remove(0);
    }
}

