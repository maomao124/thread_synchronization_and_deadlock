/**
 * Project name(项目名称)：线程的同步和死锁
 * Package(包名): PACKAGE_NAME
 * Class(类名): test3
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/11/29
 * Time(创建时间)： 20:55
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class test3
{
    public static void main(String[] args)
    {

        final StringBuffer s1 = new StringBuffer();
        final StringBuffer s2 = new StringBuffer();

        new Thread(() ->
        {
            //先拿锁一，再拿锁二
            synchronized (s1)
            {
                s1.append("a");
                s2.append("1");

                synchronized (s2)
                {
                    s1.append("b");
                    s2.append("2");

                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        }).start();

        //使用匿名内部类实现runnable接口的方式实现线程的创建
        new Thread(() ->
        {
            synchronized (s2)
            {
                s1.append("c");
                s2.append("3");

                synchronized (s1)
                {
                    s1.append("d");
                    s2.append("4");

                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        }).start();
    }
}
