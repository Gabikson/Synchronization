import java.util.concurrent.Exchanger;

public class ExchangerTest {

    public static final Exchanger<String> EXCHANGER = new Exchanger<String>();

    public static final String FIRST_MESSAGE = "Message A";
    public static final String SECOND_MESSAGE = "Message B";

    public static class ThreadObject implements Runnable {
        private int number = 0;
        private String message;

        public ThreadObject(int number, String message) {
            this.number = number;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                message = EXCHANGER.exchange(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadObject object1 = new ThreadObject(1, FIRST_MESSAGE);
        ThreadObject object2 = new ThreadObject(2, SECOND_MESSAGE);

        System.out.printf("Before:\nobject-%d: message= %s\nobject-%d: message= %s\n\n", object1.number, object1.message, object2.number, object2.message);

        Thread t1 = new Thread(object1);
        Thread t2 = new Thread(object2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.printf("After:\nobject-%d: message= %s\nobject-%d: message= %s\n", object1.number, object1.message, object2.number, object2.message);
        System.out.println();
    }
}
