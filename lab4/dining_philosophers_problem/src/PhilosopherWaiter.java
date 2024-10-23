public class PhilosopherWaiter implements Runnable {
    private final Fork leftFork;
    private final Fork rightFork;
    private final int num;
    private final Waiter waiter;

    PhilosopherWaiter(int num, Fork leftFork, Fork rightFork, Waiter waiter) {
        this.num = num;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        while (true) {
            think();

            waiter.ask();

            leftFork.take();
            System.out.println(num + " grabs left fork");
            rightFork.take();
            System.out.println(num + " grabs right fork");

            eat();

            leftFork.put();
            System.out.println(num + " puts left fork");
            rightFork.put();
            System.out.println(num + " puts right fork");

            waiter.release();
        }
    }

    private void eat() {
        System.out.println(num + " eats");

        try {
            int time = (int) (Math.random() * 10);
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void think() {
        System.out.println(num + " thinks");

        try {
            int time = (int) (Math.random() * 10);
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
