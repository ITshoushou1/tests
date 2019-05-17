package thread;


import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class WriteData implements Runnable {

    /**
     * 该线程能输出的数值
     */
    private int num;

    /**
     * 包含A, B, C, D四个文件的列表
     */
    private List<File> files;

    /**
     * 栏栅
     */
    private CyclicBarrier barrier;

    public WriteData(int num, List<File> files, CyclicBarrier barrier) {
        this.num = num;
        this.files = files;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        while (true) {
            Writer writer = null;
            try {
                // 向文件写入数值
                writer = new PrintWriter(new FileWriter(files.get(num - 1), true), true);
                writer.write(String.valueOf(num));
                // 等待其它线程完成此轮工作
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 创建 A, B, C, D四个文件
     */
    public static List<File> createFiles() {
        LinkedList<File> files = new LinkedList<>();
        final char fileChar = 'A';
        for (int i = 0; i < 4; i++) {
            String fileName = String.valueOf((char) (fileChar + i));
            files.add(new File(fileName));
        }
        return files;
    }

    /**
     * 向后偏移文件的顺序
     * A, B, C, D 经向后偏移后顺序为 D, A, B, C
     */
    public static void rorate(List<File> files) {
        Collections.rotate(files, 1);
    }

    public static void main(String[] args) {
        List<File> files = createFiles();
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            rorate(files);
        });
        for (int i = 1; i <= 4; i++) {
            new Thread(new WriteData(i, files, barrier)).start();
        }

    }
}
