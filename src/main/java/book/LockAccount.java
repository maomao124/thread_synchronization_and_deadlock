package book;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): book
 * Class(类名): LockAccount
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 21:23
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class LockAccount
{
    double balance;// 账号余额
    Lock lock = new ReentrantLock();// 创建锁对象

    public LockAccount(double balance)
    {
        super();
        this.balance = balance;
    }

    // 使用显示锁
    public void withdraw(double money)
    {

        lock.lock();// 锁定
        try
        {
            if (balance >= 1000)
            {
                try
                {
                    Thread.sleep(100);
                    balance -= 1000;
                    System.out.println("取款1000成功。");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("取款失败。");
            }

        }
        finally
        {
            lock.unlock();// 释放锁
        }
    }
}

class AccountThread3 extends Thread
{
    LockAccount account;
    int delay = 100;

    public AccountThread3(LockAccount account)
    {
        super();
        this.account = account;
    }

    @Override
    public void run()
    {
        account.withdraw(1000);
    }

    public static void main(String[] args) throws InterruptedException
    {
        // 创建账号对象
        LockAccount account = new LockAccount(2005);
        // 创建三个取款线程
        AccountThread3 t1 = new AccountThread3(account);
        AccountThread3 t2 = new AccountThread3(account);
        AccountThread3 t3 = new AccountThread3(account);
        t1.start();
        t2.start();
        t3.start();
        // 主线程等待三个线程结束后，输出最后余额
        t1.join();
        t2.join();
        t3.join();
        System.out.println("最终账号余额是：" + account.balance);
    }
}
