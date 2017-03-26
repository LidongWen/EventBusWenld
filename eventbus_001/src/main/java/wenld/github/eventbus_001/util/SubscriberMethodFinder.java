package wenld.github.eventbus_001.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import wenld.github.eventbus_001.SubscriberMethod;


/**
 *  查找方法帮助类
 * <p/>
 * Author: 温利东 on 2017/3/26 11:21.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
    public class SubscriberMethodFinder {
    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();

    /**
     * 查找方法
     * @param subscriberClass
     * @return
     */
    List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        return null;
    }
}
