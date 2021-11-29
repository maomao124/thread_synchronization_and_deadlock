package book;

/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): book
 * Class(类名): WrongAccount
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 21:32
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class WrongAccount
{
    double balance;//账号余额

    public WrongAccount(double balance)
    {
        super();
        this.balance = balance;
    }
}

class AccountThread extends Thread
{
    WrongAccount account;
    int delay = 100;

    public AccountThread(WrongAccount account)
    {
        super();
        this.account = account;
    }

    @Override
    public void run()
    {
        //下面执行取款操作
        if (account.balance >= 1000)
        {
            try
            {
                sleep(delay);
                account.balance -= 1000;
                System.out.println("取款1000成功。");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("取款失败。");
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        //创建账号对象
        WrongAccount account = new WrongAccount(1005);
        //创建三个取款线程
        AccountThread t1 = new AccountThread(account);
        AccountThread t2 = new AccountThread(account);
        AccountThread t3 = new AccountThread(account);
        t1.start();
        t2.start();
        t3.start();
        //主线程等待三个线程结束后，输出最后余额
        t1.join();
        t2.join();
        t3.join();
        System.out.println("最终账号余额是：" + account.balance);
    }
}