package reflect;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 深度拷贝工具
 *
 * @author chentao
 * @version $Id: OrikaMapper.java, v 0.1 2016年10月18日 下午5:50:04 chentao Exp $
 */
public abstract class OrikaMapper {
    /**
     * 单例
     */
    private static MapperFacade mapper = null;

    /**
     * 静态块初始化
     */
    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
    }

    /**
     * 私有构造方法
     */
    private OrikaMapper() {
    }

    /**
     * 转换对象
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
     * 转换Collection中对象
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
        return mapper.mapAsList(sourceList, destinationClass);
    }

}
