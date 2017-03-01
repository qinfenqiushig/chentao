package memcached;

import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SysMemcachedClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMemcachedClient.class);

    private static MemcachedClient memcachedClient;

    private long updateTimeout = 2500;

    private long shutdownTimeOut = 2500;

    static {
        Properties memcachedConfig = new Properties();
        try {
            memcachedConfig.load(SysMemcachedClient.class.getClassLoader().getResourceAsStream("memcached.properties"));
            memcachedClient = new MemcachedClient(new InetSocketAddress(memcachedConfig.getProperty("memcached.host"), Integer.parseInt(memcachedConfig.getProperty("memcached.port"))));
        } catch (IOException e) {
            LOGGER.error("初始化Memcached客户端错误！");
        }
    }

    /**
     * Get方法，转换结果类型并屏蔽异常，仅返回null
     *
     * @param key
     * @return
     */
    public <T> T get(String key) {
        try {
            return (T) memcachedClient.get(key);
        } catch (Exception e) {
            handleException(e, key);
            return null;
        }
    }

    /**
     * GetBulk方法，转换结果类型并屏蔽异常，仅返回null
     *
     * @param keys
     * @return
     */
    public <T> Map<String, T> getBulk(Collection<String> keys) {
        try {
            return (Map<String, T>) memcachedClient.getBulk(keys);
        } catch (Exception e) {
            handleException(e, StringUtils.join(keys, ","));
            return null;
        }
    }

    /**
     * 异步set方法，不考虑执行结果
     *
     * @param key
     * @param exp 超时时间，以秒为单位
     * @param value
     */
    public void set(String key, int exp, Object value) {
        memcachedClient.set(key, exp, value);
    }

    /**
     * 安全的Set方法，在updateTimeout时间内返回执行结果，否则返回false并取消操作。
     *
     * @param key
     * @param exp
     * @param value
     * @return
     */
    public boolean safeSet(String key, int exp, Object value) {
        Future<Boolean> future = memcachedClient.set(key, exp, value);
        try {
            return future.get(updateTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handleException(e, key);
            future.cancel(false);
        }
        return false;
    }

    /**
     * 异步delete方法，不考虑执行结果
     *
     * @param key
     */
    public void delete(String key) {
        memcachedClient.delete(key);
    }

    /**
     * 安全的delete方法，在update时间内返回执行结果，否则取消操作并返回false
     *
     * @param key
     * @return
     */
    public boolean safeDelete(String key) {
        Future<Boolean> future = memcachedClient.delete(key);
        try {
            return future.get(updateTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            handleException(e, key);
            future.cancel(false);
        }
        return false;
    }

    /**
     * Incr给key对应的value增加值,并返回增加后的结果
     *
     * @param key
     * @param by 增加的值
     * @param defaultValue 若key不存在，则给其设置默认值为defaultValue
     * @return
     */
    public long incr(String key, int by, long defaultValue) {
        return memcachedClient.incr(key, by, defaultValue);
    }

    /**
     * Decr方法
     *
     * @param key
     * @param by
     * @param defaultValue
     * @return
     */
    public long decr(String key, int by, long defaultValue) {
        return memcachedClient.decr(key, by, defaultValue);
    }

    /**
     * 可以设置有效期的Decr方法
     *
     * @param key
     * @param by
     * @param defaultValue
     * @param exp
     * @return
     */
    public long decr(String key, int by, long defaultValue, int exp) {
        return memcachedClient.decr(key, by, defaultValue, exp);
    }

    /**
     * 异步地Incr方法，没有默认值，若key不存在则返回-1
     *
     * @param key
     * @param by
     * @return
     */
    public Future<Long> asyncIncr(String key, int by) {
        return memcachedClient.asyncIncr(key, by);
    }

    public void destory() {
        if (memcachedClient != null) {
            memcachedClient.shutdown(shutdownTimeOut, TimeUnit.MILLISECONDS);
        }
    }

    private void handleException(Exception e, String key) {
        LOGGER.error("spymemcached client receive an exception with key:" + key, e);
    }

}
