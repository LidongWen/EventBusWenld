package wenld.github.eventbus_002;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 方法执行帮助类
 * <p/>
 * Author: 温利东 on 2017/3/26 11:29.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */
final class InvokeHelper {
    private final static ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static InvokeHelper ourInstance;
    private HandlerPoster handlerPoster;

    public static InvokeHelper getDefault() {
        if (ourInstance == null) {
            synchronized (InvokeHelper.class) {
                if (ourInstance == null) {
                    ourInstance = new InvokeHelper();
                }
            }
        }
        return ourInstance;
    }

    private InvokeHelper() {
        handlerPoster = new HandlerPoster(Looper.getMainLooper());
    }

    public void post(final Subscription subscription, final Object event, boolean isMainThread) {
        switch (subscription.subscriberMethod.threadMode) {
            case POSTING:
                //直接执行
                invokeSubscriber(subscription, event);
                break;
            case MAIN:
                if (isMainThread) {
                    //直接执行
                    invokeSubscriber(subscription, event);
                } else {
                    // 放在handler内执行
                    handlerPoster.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeSubscriber(subscription, event);
                        }
                    });
                }
                break;
            case BACKGROUND:
                if (isMainThread) {
                    //放在后台线程执行
//                    getExecutorService().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            invokeSubscriber(subscription,event);
//                        }
//                    });
                } else {
                    //执行
                    invokeSubscriber(subscription, event);
                }
                break;
            case ASYNC:
                //放在异步线程内执行
                getExecutorService().execute(new Runnable() {
                    @Override
                    public void run() {
                        invokeSubscriber(subscription, event);
                    }
                });
                break;
            default:
                //抛异常
                throw new IllegalStateException("Unknown thread mode: " + subscription.subscriberMethod.threadMode);
        }
    }

    private void invokeSubscriber(Subscription subscription, Object event) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
        } catch (InvocationTargetException e) {
//            throw new InvocationTargetException(subscriberMethod.subscriber,e.getCause(),event);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }

    ExecutorService getExecutorService() {
        return DEFAULT_EXECUTOR_SERVICE;
    }

    class HandlerPoster extends Handler {


        HandlerPoster(Looper looper) {
            super(looper);
        }
    }
}
