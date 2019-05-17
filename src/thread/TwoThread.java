package thread;

public class TwoThread {
    public static void main(String args[]){
        MyObject1 my = new MyObject1();
        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i = 0; i < 26; i++){
                    my.printNum();
                }
            }

        }).start();
        new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i = 0; i < 26; i++){
                    my.printA();
                }
            }

        }).start();
    }
}
class MyObject1{
    private  boolean flag = true ;
    public  int count = 1;

    public synchronized void printNum(){
        while(flag == false){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.print((2*count-1));
        System.out.print(2*count);

        flag = false;
        this.notify();
    }
    public synchronized void printA(){
        while(flag == true){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.print((char)(count+'A'-1));
        count++;
        flag = true;
        this.notify();
    }
}
