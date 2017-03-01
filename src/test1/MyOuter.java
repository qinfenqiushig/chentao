package test1;

/**
 * 1.静态内部类与静态内部方法相似，只能访问外部类的static成员，不能直接访问外部类的实例变量，与实例方法，只有通过对象引用才能访问。
 * 2.由于static内部类不具有任何对外部类实例的引用，因此static内部类中不能使用this关键字来访问外部类中的实例成员，但是可以访问外部类中的static成员。这与一般类的static方法想通
 * @author chentao
 * @version $Id: MyOuter.java, v 0.1 2016年4月21日 下午3:46:21 chentao Exp $
 */
public class MyOuter {

    public static int x = 100;

    public static class MyInner {
        private String y = "Hello!";

        public void innerMethod() {
            System.out.println("x=" + x);//访问外部类静态变量
            System.out.println("y=" + y);
        }
    }

    public static void main(String[] args) {
        MyOuter.MyInner si = new MyOuter.MyInner();//静态内部类不通过外部实例就可以创建对象；与类变量可以通过类名访问相似
        si.innerMethod();
    }
}