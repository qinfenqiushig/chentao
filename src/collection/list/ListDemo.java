package collection.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListDemo {

    private static List<String> list = new ArrayList<String>();

    static {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
    }

    public static void main(String[] args) {

        //局部变量作用域最小化
        for (Iterator<String> it = list.iterator(); it.hasNext();) {
            String str = it.next();
            if (str == "1") {
                it.remove();//remove()方法，必须先调用next()，next()必须先调用hasNext()
            }
        }
        System.out.println(list);

        for (String s : list) {
            if (s == "2") {
                //使用增强型for循环时，不能对集合的大小增减，即不能(add,remove)
                list.add("5");
                list.remove(s);
            }
        }
    }
}
