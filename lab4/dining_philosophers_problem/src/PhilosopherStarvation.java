public class PhilosopherStarvation implements Runnable {

    private final Fork leftFork;
    private final Fork rightFork;
    private final int num;
    public int eatCounter = 0;

    PhilosopherStarvation(int num, Fork leftFork, Fork rightFork) {
        this.num = num;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (true) {
            think();

            if(leftFork.tryTake() && rightFork.tryTake()) {
                System.out.println(num + " grabs left and right fork");

                eat();
            }

            leftFork.put();
            System.out.println(num + " puts left fork");
            rightFork.put();
            System.out.println(num + " puts right fork");
        }
    }

    private void eat() {
        System.out.println(num + " eats");
        eatCounter++;

        try {
            int time = (int) (num * Math.random() * 1000);
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void think() {
        System.out.println(num + " thinks");

        try {
            int time = (int) (Math.random() * 1000);
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

