import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    public static int OBJECT_COUNT = 5;
    public static  final Semaphore SEMAPHORE = new Semaphore(1, true);

    private static Object object = new Object();

    public static class ThreadObject implements Runnable {

        private int number = 0;

        public ThreadObject(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            try {
                System.out.println("ThreadObject-" + number + " try to enter synchronized block...");
                SEMAPHORE.acquire();

                synchronized (object) {
                    System.out.println("ThreadObject-" + number + " in synchronized block...");
                    Thread.sleep(5000);
                }

                SEMAPHORE.release();
                System.out.println("ThreadObject-" + number + " go out from synchronized block...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 1; i <= OBJECT_COUNT; i++) {
            new Thread(new ThreadObject(i)).start();
        }
    }
}
