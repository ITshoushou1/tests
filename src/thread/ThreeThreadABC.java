package thread;

/**
 * 编写一个程序，启动三个线程，三个线程的ID分别是A，B，C；，每个线程将自己的ID值在屏幕上打印5遍，打印顺序是ABCABC...
 */

public class ThreeThreadABC {

    public static void main(String[] args) {
        PrintRunner printer = new PrintRunner();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                printer.printA();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                printer.printB();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                printer.printC();
            }
        }).start();
    }

}


class PrintRunner  {

    private int flag = 0;



    public synchronized void printA(){

        try {
            while (flag != 0) {
                this.wait();
            }
            Thread.sleep(500);
            System.out.println("A");
            flag=1;
            this.notifyAll();
        } catch (InterruptedException e) {

        }
    }

    public synchronized void printB(){
        try {
            while (flag != 1) {
                this.wait();
            }
            Thread.sleep(500);
            System.out.println("B");
            flag=2;
            this.notifyAll();
        } catch (InterruptedException e) {

        }
    }

    public synchronized void printC(){
        try {
            while (flag != 2) {
                this.wait();
            }
            Thread.sleep(500);
            System.out.println("C");
            flag=0;
            this.notifyAll();
        } catch (InterruptedException e) {

        }
    }
}