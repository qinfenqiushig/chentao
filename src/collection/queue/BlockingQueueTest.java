/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package collection.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 下面实例可以看出，使用阻塞队列两个显著的好处就是：
 * 多线程操作共同的队列时不需要额外的同步，
 * 另外就是队列会自动平衡负载，即那边（生产与消费两边）处理快了就会被阻塞掉，从而减少两边的处理速度差距
 * @author chentao
 * @version $Id: BlockingQueueTest.java, v 0.1 2016年6月7日 下午3:36:08 chentao Exp $
 */
public class BlockingQueueTest {
    //搜索起始目录
    private static final String START_PATH = "resource";
    //阻塞队列容量
    private static final int MAX_SIZE = 10;
    //关键字
    private static final String KEY_WORD = "Java";

    public static void main(String[] args) {
        File startFile = new File(START_PATH);
        BlockingQueue<File> fileQueque = new ArrayBlockingQueue<File>(MAX_SIZE);
        FileEnumerationTask allFile = new FileEnumerationTask(fileQueque, startFile);
        Thread mainTask = new Thread(allFile);
        mainTask.start();

        Boolean isNoFile = false;
        for(int i=0;i<9;i++){
            new Thread(new SearchTask(fileQueque, KEY_WORD,isNoFile)).start();
        }
    }
}

/**
 * 将制定目录下的所有文件，放在阻塞队列中
 *
 * @author chentao
 * @version $Id: BlockingQueueTest.java, v 0.1 2016年6月7日 下午3:46:53 chentao Exp $
 */
class FileEnumerationTask implements Runnable{
    //空文件放在阻塞队列最后，表明已经遍历完毕
    public static final File DUMMY = new File("");
    private BlockingQueue<File> fileQueue;
    private File startFile;

    /**
     * 构造方法
     */
    public FileEnumerationTask(BlockingQueue<File> fileQueque,File startFile) {
        this.fileQueue = fileQueque;
        this.startFile = startFile;
    }

    public void run(){
        try {
            enumeration(startFile);
            fileQueue.put(DUMMY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void enumeration(File file) throws InterruptedException{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                enumeration(files[i]);
            }
        }else{
            //put和take方法或产生阻塞效果
            fileQueue.put(file);
            System.out.println("向阻塞队列中存入文件："+file.getName());
        }
    }
}

class SearchTask implements Runnable{

    private BlockingQueue<File> queque;
    private String keyWord;
    private Boolean isNoFile;

    public SearchTask(BlockingQueue<File> queque,String keyWord,Boolean isNoFile) {
        this.queque = queque;
        this.keyWord = keyWord;
        this.isNoFile =isNoFile;
    }

    public void run(){
        try{
            while(!isNoFile){
                File file = queque.take();
                if(file == FileEnumerationTask.DUMMY){
                    isNoFile = true;
                }else{
                    search(file);
                }
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void search(File file) throws FileNotFoundException{
        Scanner scan = new Scanner(new FileInputStream(file));
        int lineNum = 0;
        while(scan.hasNext()){
            String line = scan.nextLine();
            lineNum++;
            if(line.contains(keyWord)){
                System.out.printf("从文件%s的第%d行中,找到关键字：%s,%n",file.getPath(),lineNum,line);
            }
        }
        scan.close();
    }
}