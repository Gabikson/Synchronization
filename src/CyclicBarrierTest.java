import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static final int THREADS_COUNT = 9;
    public static final int READY_COUNT = 3;

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(READY_COUNT, new ReadyWorker());

    public static class ReadyWorker implements Runnable {

        @Override
        public void run() {
            System.out.println("Barrier is broken...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThreadObject implements Runnable {
        private int number = 0;

        public ThreadObject(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            try {
                System.out.println("ThreadObject-" + number + " is waiting near barrier...");
                cyclicBarrier.await();
                System.out.println("ThreadObject-" + number + " is running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new CyclicBarrierTest.ThreadObject(i)).start();
            Thread.sleep(500);
        }
    }
}
