/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chentao
 * @version $Id: GenericTest.java, v 0.1 2016年6月7日 上午11:52:14 chentao Exp $
 */
public class GenericTest {

    private Map<String,Integer> map = new HashMap<String,Integer>();

    public static void main(String[] args) throws NoSuchFieldException, SecurityException {
        Class<GenericTest> clas = GenericTest.class;
        Field field = clas.getDeclaredField("map");
        Class<?> type = field.getType();
        System.out.println("map字段属性为："+type.getName());

        Type genericType = field.getGenericType();
        if(genericType instanceof ParameterizedType){
            ParameterizedType ptype = (ParameterizedType)genericType;

            //获取该字段包含中的基本类型
            Type basicType = ptype.getRawType();
            System.out.println("基本类型为："+basicType);
            //获取该字段所有泛型信息
            Type[] types = ptype.getActualTypeArguments();
            for(int i=0;i<types.length;i++){
                System.out.println("第"+(i+1)+"个属性类型为:"+types[i]);
            }
        }
    }
}
