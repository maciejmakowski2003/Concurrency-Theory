public class PhilosopherAsymmetric implements Runnable {
    private final Fork leftFork;
    private final Fork rightFork;
    private final int num;

    PhilosopherAsymmetric(int num, Fork leftFork, Fork rightFork) {
        this.num = num;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();

                if (num % 2 == 1) {
                    leftFork.take();
                    System.out.println(num + " grabs left fork");
                    Thread.sleep(100);
                    rightFork.take();
                    System.out.println(num + " grabs right fork");
                } else {
                    rightFork.take();
                    System.out.println(num + " grabs right fork");
                    Thread.sleep(100);
                    leftFork.take();
                    System.out.println(num + " grabs left fork");
                }

                eat();

                leftFork.put();
                System.out.println(num + " puts left fork");
                rightFork.put();
                System.out.println(num + " puts right fork");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
