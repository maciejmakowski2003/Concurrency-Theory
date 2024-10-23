import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int philosophersNumber = 5;
        var philosophers = new ArrayList<PhilosopherStarvation>();
        var forks = new ArrayList<Fork>();
        var threads = new ArrayList<Thread>();

//        var waiter = new Waiter(philosophersNumber);

        for (int i = 0 ; i < philosophersNumber ; i++) {
            forks.add(new Fork());
        }

        for (int i = 0 ; i < philosophersNumber ; i++) {
            var philosopher = new PhilosopherStarvation(i, forks.get(i), forks.get((i+1) % philosophersNumber));
            philosophers.add(philosopher);
        }

//        for (int i = 0 ; i < philosophersNumber ; i++) {
//            var philosopher = new PhilosopherWaiter(i, forks.get(i), forks.get((i+1) % philosophersNumber), waiter);
//            philosophers.add(philosopher);
//        }

        for (var philosopher : philosophers) {
            var thread = new Thread(philosopher);
            thread.start();
            threads.add(thread);
        }

        try {
            Thread.sleep(20_000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (var philosopher : philosophers) {
            System.out.println(philosopher.eatCounter);
        }
    }
}