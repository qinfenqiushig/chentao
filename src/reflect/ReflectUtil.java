package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 反射工具类
 *
 * @author chentao
 * @version $Id: ReflectUtil.java, v 0.1 2016年10月18日 上午11:59:36 chentao Exp $
 */
public class ReflectUtil {

    private static final String GETTER_PREFIX = "get";

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX_IS = "is";

    /**
     * 调用GETTER方法
     *
     */
    public static Object invokeGetter(Object obj, String propertyName){
        String getterMethodName;
        if(StringUtils.equalsIgnoreCase(propertyName, "arrived")) getterMethodName = GETTER_PREFIX_IS + StringUtils.capitalize(propertyName);
        else getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        return invokeMethod(obj,getterMethodName,new Class[]{},new Object[]{});
    }

    /**
     * 调用SETTER方法
     *
     * @param obj
     * @param propertyName
     * @param arg
     */
    public static void invokeSetter(Object obj,String propertyName,Object arg){
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        invokeMethodByName(obj,setterMethodName,new Object[]{arg});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(Object obj,String fieldName){
        Field field = getAccessibleField(obj,fieldName);
        try {
            return field.get(obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("获取Field [ "+ fieldName +" ] 错误");
        }
    }

    /**
     * 获取所有的fileds
     *
     * @param obj
     * @return
     */
    public static List<Field> getDeclaredFields(Object obj){
        List<Field> allFields = new ArrayList<Field>();
        for(Class<?> searchType = obj.getClass();searchType!=Object.class;searchType=searchType.getSuperclass()){
            Field[] fields = searchType.getDeclaredFields();
            for(Field field:fields){
                allFields.add(field);
            }
        }
        return allFields;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * <p/>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(Object obj,String fieldName){
        for(Class<?> searchType = obj.getClass(); searchType != Object.class;searchType = searchType.getSuperclass()){
            try {
                Field field = searchType.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException | SecurityException e) {
                // Field不在当前类，继续向上转型
            }
        }
        return null;
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field){
        if(!Modifier.isPublic(field.getModifiers())||!Modifier.isPublic(field.getClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())||!field.isAccessible()){
            field.setAccessible(true);
        }
    }

    /**
     * 根据方法名调用方法，无视private/protected修饰符，若有重名方法，则调用第一个。
     *
     * @param obj
     * @param methodName
     * @param args
     */
    public static void invokeMethodByName(Object obj,String methodName,Object[] args){
        Method method = getAccessibleMethodByName(obj,methodName);
        try {
            method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException("执行方法 [ "+methodName+" ] 出错！");
        }
    }

    /**
     * 循环向上转型，获取DeclaredMethod，并强制转为可使用。
     * 只匹配方法名。
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Method getAccessibleMethodByName(Object obj,String methodName){
        for(Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()){
            Method[] methods = searchType.getDeclaredMethods();
            for(Method method:methods){
                if(method.getName().equals(methodName)){
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     *
     * <ol>直接调用对象方法
     * <li>无视private/protected修饰符。
     * <li>用于一次性调用的情况，否则用{@link #getAccessibleMethod(Object, String, Class[])}函数获得方法后反复调用。
     * <li>根据方法名和参数类型获取方法。
     * </ol>
     *
     * @param obj 需要反射的对象
     * @param methodName 需要调用的方法名
     * @param parameterTypes 方法的参数类型数组
     * @param args 方法的参数值
     * @return
     */
    public static Object invokeMethod(final Object obj,final String methodName,final Class<?>[] parameterTypes,final Object[] args ){
        Method method = getAccessibleMethod(obj,methodName,parameterTypes);
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException("执行方法 [ "+methodName+" ] 出错！");
        }
    }

    /**
     * 循环向上转型，获得对象的DeclaredMethod，若向上到Object类型还没找到方法，则返回null。
     * 根据方法的方法名和参数类型获取方法。
     * <p>
     * 用于方法需要被多次调用的情况。
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getAccessibleMethod(final Object obj,final String methodName,final Class<?>[] parameterTypes){
        for(Class<?> searchType = obj.getClass();searchType != Object.class;searchType = searchType.getSuperclass()){
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method 不在当前类定义，继续向上转型
            }
        }
        return null;
    }

    /**
     * 改变private/protected修饰的方法为public，尽量不调用实际的语句，避免JDK的SecurityManager报错
     *
     * @param method
     */
    public static void makeAccessible(Method method){
        if(!Modifier.isPublic(method.getModifiers())||!Modifier.isPublic(method.getDeclaringClass().getModifiers())
                ||!method.isAccessible() || Modifier.isFinal(method.getModifiers())){
            method.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class. eg. public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     * <p/>
     * <pre>
     *      public UserDao extends HibernateDao<User,Long> {
     *
     *      }
     * </pre>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    public static Class getClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }
}
