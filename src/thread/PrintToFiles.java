package thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 现在有四个文件file1,file2,file3, file4。初始都为空。 现要让四个文件呈如下格式： file1：A B C D A B.... file2：B C D A B C.... file3：C D A B C D.... file4：D A B C D A....
 */
public class PrintToFiles {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        List<MyFile> myFiles = new ArrayList<>(4);
        char start ='A';
        for (int i = 1; i <= 4; i++) {
            MyFile myFile = new MyFile(path + "\\file" + i + ".txt",start++);
            if (!myFile.exists()) {
                try {
                    myFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            myFiles.add(myFile);
        }

        FilesPrinter filesPrinter = new FilesPrinter();
        filesPrinter.setFiles(myFiles);

        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {

                    filesPrinter.printA();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {

                    filesPrinter.printB();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {

                    filesPrinter.printC();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                for (int i = 0; i < 20; i++) {

                    filesPrinter.printD();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }







}

class FilesPrinter {

    private List<MyFile> files;
    private MyFile file;

    public void printA() throws IOException {
        char A = 'A';
            synchronized ((files)) {
                int count =0;
                while (count < files.size()) {
                    for (MyFile file : files) {
                        try {

                            if (file.shouldPrint == A) {
                                file.append(A);
                                file.shouldPrint = 'B';
                                System.out.println(Thread.currentThread().getName()+"--- printA "+"writ to  "+file.getName()+": "+A + " shoutPrint:"+ file.shouldPrint);
                                count++;
                            }
                            // System.out.println(Thread.currentThread().getName()+"--- printA "+file.getName()+" waitting ....");
                        } catch (Exception  e) {
                            e.printStackTrace();
                        }
                    }
                    if (count < files.size()) {
                        try {
                            files.notifyAll();
                            files.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        files.notifyAll();
                    }
                }

            }
    }

    public void printB() throws IOException {
        char B = 'B';

            synchronized ((files)) {
                int count =0;
                while (count < files.size()) {
                    for (MyFile file : files) {
                        try {


                            if (file.shouldPrint == B) {
                                file.append(B);
                                file.shouldPrint = 'C';
                                System.out.println(Thread.currentThread().getName() + "--- printB " + "writ to  " + file.getName() + ": " + B);


                            }

                            // System.out.println(Thread.currentThread().getName() + "--- printB " + file.getName() + " waitting ....");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (count < files.size()) {
                        try {
                            files.notifyAll();
                            files.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        files.notifyAll();
                    }

                }

            }
            files.notifyAll();


    }

    public void printC() throws IOException {
        char C = 'C';


            synchronized ((files)) {
                int count =0;
                while (count < files.size()) {
                    for (MyFile file : files) {
                        try {
                            boolean flag = false;
                            if (file.shouldPrint == C) {
                                file.append(C);
                                file.shouldPrint = 'D';
                                System.out.println(Thread.currentThread().getName() + "--- printC " + "writ to  " + file.getName() + ": " + C);


                            }

                            // System.out.println(Thread.currentThread().getName()+"--- printC "+file.getName()+" waitting ....");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (count < files.size()) {
                        try {
                            files.notifyAll();
                            files.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        files.notifyAll();
                    }
                }
            }


    }

    public void printD() throws IOException {
        char D = 'D';


            synchronized ((files)) {
                int count = 0;
                while (count < files.size()) {
                    for (MyFile file : files) {
                        try {
                            boolean flag = false;
                            if (file.shouldPrint == D) {
                                file.append(D);
                                System.out.println(Thread.currentThread().getName() + "--- printD " + "writ to  " + file.getName() + ": " + D);
                                file.shouldPrint = 'A';

                            }

                            // System.out.println(Thread.currentThread().getName()+"--- printD "+file.getName()+" waitting ....");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (count < files.size()) {
                        try {
                            files.notifyAll();
                            files.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        files.notifyAll();
                    }
                }
            }


    }

    public void setFiles(List files) {
        this.files = files;
    }
}

class MyFile extends File{

    char shouldPrint;
    FileWriter fw ;

    public MyFile(String pathname, char shouldPrint) {
        super(pathname);
        this.shouldPrint = shouldPrint;
        try {
            fw = new FileWriter(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyFile(String pathname) {
        super(pathname);
    }

    public void append(char c) throws IOException {
        fw.append(c);
        fw.flush();
    }
}

