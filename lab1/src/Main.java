import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        numberOfThreadsTest();
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }

    private static void raceConditionTest() {
        var repetitions = 100000;

        var incrementor = new Thread(new Incrementor(repetitions));
        var decrementor = new Thread(new Decrementor(repetitions));

        incrementor.start();
        decrementor.start();

        try {
            incrementor.join();
            decrementor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Counter.getCounter());
    }

    private static void numberOfThreadsTest() {
        var threadsCount = 5000000;
        var threads = new ArrayList<Thread>();

        for(int i = 0; i < threadsCount; i++) {
            threads.add(new Thread(new Incrementor(1)));
            threads.add(new Thread(new Decrementor(1)));
        }

        threads.forEach(Thread::start);

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Counter.getCounter());
    }
}