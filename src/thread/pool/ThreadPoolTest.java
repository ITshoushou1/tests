package thread.pool;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 50, TimeUnit.SECONDS,workQueue,new ThreadPoolExecutor.CallerRunsPolicy());

        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+ " thread running 1 ........... and then sleep 2000ms");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sleep End ! ");
        });
        // for (int i = 1; i < 10; i++) {
        //     System.out.println(i);
        //     pool.execute(()->{
        //         System.out.println(Thread.currentThread().getName()+ " thread running .......");
        //     });
        // }

      /*  ExecutorService executorService =  Executors.newFixedThreadPool(10);
        executorService.submit(() -> {
            System.out.println(" running ....");
            return 1;
        });*/
    }
}
