package wenld.github.eventbus_001;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p/>
 * Author: 温利东 on 2017/3/26 10:44.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class EventBus {
    private static EventBus ourInstance;

    private final Map<Class<?>, CopyOnWriteArrayList<SubscriberMethod>> cacheMap;
    private final Map<Object, List<Class<?>>> typesBySubscriber;
    //粘性
    private final Map<Class<?>, Object> stickyEvents;

    public static EventBus getDefault() {
        if (ourInstance == null) {
            synchronized (EventBus.class) {
                if (ourInstance == null) {
                    ourInstance = new EventBus();
                }
            }
        }
        return ourInstance;
    }

    private EventBus() {
        //初始化一些数据
        cacheMap = new HashMap<>();
        stickyEvents = new ConcurrentHashMap<>();
        typesBySubscriber=new HashMap<>();
    }

    public void register(Object subscriber) {
        //1.找到所有方法
        //2.保存订阅  subscriber();
    }

    public void unRegister(Object subscriber) {
        //去除订阅
    }
    public void post(Object obj) {
        //1.获取缓存 进行判断
        //2.指定线程执行
    }
    public void postSticky(Object obj){
        //加入粘性缓存 stickyEvents
        //执行
    }

    /**
     *  保存订阅方法
     * @param subscriber
     */
    private void subscriber(Object subscriber) {
        //1.保存数据  ，  如果重复 抛异常
        //2.执行粘性事件
    }
}
