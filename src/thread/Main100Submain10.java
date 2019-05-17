package thread;

public class Main100Submain10 {


    public static void main(String args[]){
        MyObject2 m = new MyObject2();
        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i = 0; i<5; i++)
                    m.sub();
            }

        }).start();

        for(int i = 0; i<5; i++)
            m.main1();
    }
}

class MyObject2{
    private boolean flag = true;//flag是true时执行sub方法，flag是false时执行main1方法
    public synchronized void sub(){
        while(flag == false){ //如果flag==false，说明另一个线程拥有该对象的锁，调用sub的方法被阻塞，直到另一个线程释放锁，唤醒该线程。
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for(int i = 0 ; i<10; i++){
            System.out.print("s");
        }
        System.out.println();
        flag = false;
        this.notify();
    }

    public synchronized void main1(){
        while(flag == true){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for(int i = 0 ; i<100; i++){
            System.out.print("m");
        }
        System.out.println();
        flag = true;
        this.notify();
    }

}