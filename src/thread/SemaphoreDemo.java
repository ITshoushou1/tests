package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {


    public static void main(String[] args) {

        Semaphore s = new Semaphore(10);
        for(int i = 0 ; i<20 ;i++){
            Restaurant res = new Restaurant(s);
            res.start();
        }

    }
}

class Restaurant extends Thread{

    Semaphore s = null;

    public Restaurant(Semaphore s) {
        this.s = s;
    }

    @Override
    public void run() {

        try {
            //获取一个许可
            s.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("一位客人占了一个座位");

        try {
            TimeUnit.SECONDS.sleep((long) (10*Math.random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s.release(1);
        System.out.println("一位客人起身离开了");
    }
}
