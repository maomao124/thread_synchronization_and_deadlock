package book;

/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): book
 * Class(类名): SynThreads
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 21:29
 * Version(版本): 1.0
 * Description(描述)： 无
 */

class SynAccount
{
    double balance;//余额
    final double MAX = 10000;//最高限额

    public SynAccount(double balance)
    {
        this.balance = balance;
    }

    //存款同步方法
    public synchronized void withdraw(double money)
    {
        if (balance < money)
        {
            try
            {
                System.out.printf("取款%1$,.2f失败。余额：%2$,.2f\n", money, balance);
                wait();//进入等待队列
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        balance -= money;
        System.out.printf("取款%1$,.2f成功。余额：%2$,.2f\n", money, balance);
        notify();//唤醒等待队列的线程

    }

    //取款同步方法
    public synchronized void deposit(double money)
    {
        if (balance + money >= MAX)
        {
            try
            {
                System.out.printf("存款%1$,.2f失败。余额：%2$,.2f\n", money, balance);
                wait();//进入等待队列
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        balance += money;
        System.out.printf("存款%1$,.2f成功。余额：%2$,.2f\n", money, balance);
        notify();//唤醒等待队列的线程

    }
}

public class SynThreads
{
    //取款线程
    static class Withdrawer extends Thread
    {
        SynAccount account;

        public Withdrawer(SynAccount account)
        {
            super();
            this.account = account;
        }

        @Override
        public void run()
        {
            for (int i = 0; i < 8; i++)
            {
                account.withdraw(2000);
            }
        }

    }

    //存款线程
    static class Depositor extends Thread
    {
        SynAccount account;

        public Depositor(SynAccount account)
        {

            this.account = account;
        }

        @Override
        public void run()
        {
            for (int i = 0; i < 8; i++)
            {
                account.deposit(2000);
            }
        }
    }

    public static void main(String[] args)
    {
        SynAccount account = new SynAccount(5000);
        Thread withdraw = new Withdrawer(account);
        Thread deposit = new Depositor(account);
        withdraw.start();
        deposit.start();
        ;
    }
}