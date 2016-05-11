import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(5);

    public static class ThreadObject implements Runnable {
        private int number = 0;

        public ThreadObject(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            COUNT_DOWN_LATCH.countDown();
            try {
                System.out.println("ThreadObject-" + number + " is waiting for others...");
                COUNT_DOWN_LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadObject-" + number + " is running...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadObject(i)).start();
            Thread.sleep(3000);
        }
    }
}
