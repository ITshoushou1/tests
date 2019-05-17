package thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch cdl = new CountDownLatch(5);

        new Thread(new Teacher(cdl)).start();
        new Thread(new Student(cdl)).start();
        new Thread(new Student(cdl)).start();
        new Thread(new Student(cdl)).start();
        new Thread(new Student(cdl)).start();

        // 表示让线程阻塞，直到计数归零的时候阻塞才能放开
        cdl.await();

        System.out.println("考试结束~~~");

    }
}

class Student implements Runnable {

    private CountDownLatch cdl;

    public Student(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {

        System.out.println("学生来到考场，准备考试~~~");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("学生交卷离开考场");

        // 计数-1
        cdl.countDown();
    }
}

class Teacher implements Runnable {

    private CountDownLatch cdl;

    public Teacher(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {

        System.out.println("老师来到考场，准备考试~~~");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("老师收卷离开了考场~~~");

        cdl.countDown();
    }

}
