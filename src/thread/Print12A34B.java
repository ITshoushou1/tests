package thread;

import org.junit.Test;

/**
 * 　1.写两个线程，一个线程打印1~52，另一个线程打印A~Z，打印顺序是12A34B...5152Z；
 */
public class Print12A34B {


    public static void main(String[] args) {
      /*  Object lock = new Object();

        new Thread123(lock).start();
        new ThreadABC(lock).start();*/

        Print12A34B  printer = new Print12A34B();
       new Thread(()->{
           int end = 52;
           for (int i = 1; i <= end; i+=2) {
               printer.print123(i);

           }

       }).start();

        new Thread(()->{
            int end = 'Z';
            for (int i = 'A'; i <= end; i++) {
                printer.printAbc(i);

            }

        }).start();


    }

     /**
     * Junit单元测试不支持多线程
     */
    @Test
    public void testPrint12A34B(){
      /* Print12A34B  printer = new Print12A34B();
       new Thread(()->{
           int end = 52;
           for (int i = 1; i <= end; i+=2) {
               printer.print123(i);

           }

       }).start();

        new Thread(()->{
            int end = 'Z';
            for (int i = 'A'; i <= end; i++) {
                printer.printAbc(i);
            }

        }).start();*/

    }

    private boolean flag=false;


    public synchronized void print123(int i){
        try {
            while (flag) {
                this.wait();
            }

            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notify();
        System.out.print(i);
        System.out.print(++i);
        flag=true;
    }

    public synchronized void printAbc(int c) {
        try {
            while (!flag) {
                this.wait();
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.notify();
        System.out.print((char)c);
        flag=false;
    }

}


class  Thread123 extends Thread{
    private Object lock;

    public Thread123( Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int end =52;
        for (int i = 1; i <= end; i++) {
            synchronized (lock) {
                try {
                    if (i>2 && i % 2 != 0) {
                        lock.wait();
                   }
                   System.out.println(i);
                    lock.notify();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class  ThreadABC extends Thread{
    private Object lock;

    public ThreadABC(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        int end = 'Z';
        for (int i = 'A'; i <= end; i++) {

            synchronized (lock) {
                try {

                    lock.notify();
                    System.out.println((char) i);
                    Thread.sleep(1000);
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}