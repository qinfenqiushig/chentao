/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.lock;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author chentao
 * @version $Id: TestCount.java, v 0.1 2016年6月6日 上午10:35:14 chentao Exp $
 */
public class TestLock {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        MyCount myCount = new MyCount("199112220100001", new BigDecimal("10000"));
        Lock lock = new ReentrantLock();

        User user1 = new User("曹操", myCount, lock);
        User user2 = new User("曹丕", myCount, lock);
        User user3 = new User("曹植", myCount, lock);
        User user4 = new User("曹仁", myCount, lock);

        Integer random = 1;
        Thread t1 = new Thread(new UserThread(user1,random = random == 1 ? 0 : 1));
        Thread t2 = new Thread(new UserThread(user2,random = random == 1 ? 0 : 1));
        Thread t3 = new Thread(new UserThread(user3,random = random == 1 ? 0 : 1));
        Thread t4 = new Thread(new UserThread(user4,random = random == 1 ? 0 : 1));

        threadPool.execute(t1);
        threadPool.execute(t2);
        threadPool.execute(t3);
        threadPool.execute(t4);

        threadPool.shutdown();
    }
}

class UserThread implements Runnable {

    private User user;
    private Integer  random;

    UserThread(User user,Integer random) {
        this.user = user;
        this.random = random;
    }

    public void run() {
        user.getLock().lock();
        if (random == 1) {
            user.getMyCount().putCash(new BigDecimal("5000"));
            System.out.println(user.getName() + "存入金额：" + "5000，"+"当前余额为："+user.getMyCount().getCash());
        } else {
            user.getMyCount().takeCash(new BigDecimal("4000"));
            System.out.println(user.getName() + "取出金额：" + "4000，"+"当前余额为："+user.getMyCount().getCash());
        }
        user.getLock().unlock();
    }
}
