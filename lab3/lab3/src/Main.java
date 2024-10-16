public class Main {
    public static void main(String[] args) {
        var buffer = new Buffer();
        var consumerNumber = 4;
        var producerNumber = 6;

        for (int i = 0; i < consumerNumber; i++) {
            var consumer = new Consumer(buffer);
            consumer.start();
        }

        for (int i = 0; i < producerNumber; i++) {
            var producer = new Producer(buffer);
            producer.start();
        }
    }
}