package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(1);

        CyclicBarrier barrier = new CyclicBarrier(10);


        Thread caipan = new Thread(new CaiPan(latch));

        caipan.start();


        for (int i = 0 ; i<10 ;i++){

            Thread sport =  new Thread(new SportMan(barrier));

            sport.start();
        }

    }

}

class SportMan implements Runnable{
    CyclicBarrier barrier = null;

    public SportMan(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()+"选手到达起跑线");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"冲出了起跑线");

    }
}
class CaiPan implements  Runnable{
    CountDownLatch latch = null;

    public CaiPan(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("比赛开始");
        latch.countDown();
    }
}
