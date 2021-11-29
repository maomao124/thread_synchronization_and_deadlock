package book;

/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): book
 * Class(类名): test5
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 21:18
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class test5 extends Thread
{
    static int result = 0;

    public test5(String name)
    {
        super(name);
    }

    public static void main(String[] args)
    {
        System.out.println("主线程执行");
        Thread t = new test5("计算线程");
        t.start();
        System.out.println("result：" + result);
        try
        {
            long start = System.nanoTime();
            t.join(10);
            long end = System.nanoTime();
            t.interrupt();
            System.out.println((end - start) / 1000000 + "毫秒后:" + result);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        System.out.println(this.getName() + "开始计算...");
        for (int i = 0; i < 10000000; i++)
        {
            result++;
            if (Thread.interrupted())
            {
                System.out.println(this.getName() + "被中断，即将结束");
                return;
            }
        }
        System.out.println(this.getName() + "结束计算");
    }
}
