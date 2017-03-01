/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

/**
 *一开始我也猜错了，但是后来想通了，之前总以为调用构造函数之前必须完成类的初始化，其实不一定。
 *可以从类的生命周期和对象的初始化来分析：
 *1.类的生命周期是：加载->验证->准备->解析->初始化->使用->卸载，只有在准备阶段和初始化阶段才会涉及类变量的初始化和赋值，因此只针对这两个阶段进行分析；
 *2.类的准备阶段需要做是为类变量分配内存并设置默认值，因此类变量st为null、b为0；（需要注意的是如果类变量是final在加载阶段就已经完成了初始化，可以把b设置为final试试）；
 *3.类的初始化阶段需要做是执行类构造器（类构造器是编译器收集所有静态语句块和类变量的赋值语句按语句在源码中的顺序合并生成类构造器，对象的构造方法是init，类的构造方法是cinit，可以在堆栈信息中看到），
 *  因此先执行第一条静态变量的赋值语句即st = new StaticTest ()，此时会进行对象的初始化，对象的初始化是先初始化成员变量再执行构造方法，因此打印2->设置a为110->执行构造方法(打印3,此时a已经赋值为110，但是b只是设置了默认值0，并未完成赋值动作)，
 *等对象的初始化完成后继续执行之前的类构造器的语句，接下来就不详细说了，按照语句在源码中的顺序执行即可；
 * @author chentao
 * @version $Id: StaticTest.java, v 0.1 2016年3月23日 上午10:45:21 chentao Exp $
 */
public class StaticTest {

    public static void main(String[] args) {
        function();
    }

    static StaticTest staticTest = new StaticTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void function() {
        System.out.println("4");
    }

    int        a = 110;
    static int b = 112;
}
