/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): PACKAGE_NAME
 * Class(类名): test4
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 20:59
 * Version(版本): 1.0
 * Description(描述)： 生产者/消费者问题
 */

class Clerk
{
    private int productCount = 0;
    //生产产品
    public synchronized void produceProduct()
    {
        if (productCount < 20)
        {
            productCount++;
            System.out.println(Thread.currentThread().getName() + ":开始生产第" + productCount + "个产品");
            notify();
        }
        else
        {
            //当有20个时，等待wait
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    //消费产品
    public synchronized void consumeProduct()
    {
        if (productCount > 0)
        {
            System.out.println(Thread.currentThread().getName() + ":开始消费第" + productCount + "个产品");
            productCount--;
            notify();
        }
        else
        {
            //当0个时等待
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread
{//生产者线程

    private Clerk clerk;

    public Producer(Clerk clerk)
    {
        this.clerk = clerk;
    }

    @Override
    public void run()
    {
        try
        {
            sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ";开始生产产品......");

        while (true)
        {
            clerk.produceProduct();
        }
    }
}

class Consumer implements Runnable
{//消费者线程
    private Clerk clerk;

    public Consumer(Clerk clerk)
    {
        this.clerk = clerk;
    }

    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + ":开始消费产品");
        while (true)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }

    }
}


public class test4
{
    public static void main(String[] args)
    {
        Clerk clerk = new Clerk();

        Producer p1 = new Producer(clerk);
        p1.setName("生产者1");

        Consumer c1 = new Consumer(clerk);
        Thread t1 = new Thread(c1);
        t1.setName("消费者1");

        p1.start();
        t1.start();

    }
}
