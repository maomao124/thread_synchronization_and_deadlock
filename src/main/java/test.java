/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): PACKAGE_NAME
 * Class(类名): test
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 20:35
 * Version(版本): 1.0
 * Description(描述)： 无
 */

class MyThread implements Runnable
{
    private int ticket = 10;

    @Override
    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            synchronized (this)
            {
                if (ticket > 0)
                {
                    try
                    {
                        Thread.sleep(50);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖票：" + "卖完剩余：" + (ticket - 1) + "张");
                    ticket = ticket - 1;
                }
            }
        }
    }
}

public class test
{
    public static void main(String[] args)
    {
        MyThread thread = new MyThread();
        Thread t1 = new Thread(thread, "线程1");
        Thread t2 = new Thread(thread, "线程2");
        Thread t3 = new Thread(thread, "线程3");
        t2.start();
        t1.start();
        t3.start();
    }
}
