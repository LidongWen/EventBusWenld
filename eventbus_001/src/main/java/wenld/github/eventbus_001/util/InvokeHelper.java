package wenld.github.eventbus_001.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wenld.github.eventbus_001.SubscriberMethod;

/**
 *  方法执行帮助类
 * <p/>
 * Author: 温利东 on 2017/3/26 11:29.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
public class InvokeHelper {


    public static void post(SubscriberMethod subscriberMethod,Object event,boolean isMainThread) {
        switch (subscriberMethod.threadMode) {
            case POSTING:
                //直接执行
                break;
            case MAIN:
                if (isMainThread) {
                    //直接执行
                } else {
                    // 放在handler内执行
                }
                break;
            case BACKGROUND:
                if (isMainThread) {
                    //放在后台线程执行
                } else {
                    //执行
                }
                break;
            case ASYNC:
                //放在异步线程内执行
                break;
            default:
                //抛异常
                throw new IllegalStateException("Unknown thread mode: " + subscriberMethod.threadMode);
        }
    }
}
