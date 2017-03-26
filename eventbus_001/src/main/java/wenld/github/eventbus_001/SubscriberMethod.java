package wenld.github.eventbus_001;

import java.lang.reflect.Method;

/**
 * <p/>
 * Author: 温利东 on 2017/3/23 17:56.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class SubscriberMethod {
    public final Object subscriber;
    public  final Method method;
    public  final ThreadMode threadMode;
    public  final Class<?> eventType;       //参数
    public  final int priority;
    public    final boolean sticky;
    /**
     * Used for efficient comparison
     */
   public String methodString;

    public SubscriberMethod(Object subscriber, Method method, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.subscriber = subscriber;
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.priority = priority;
        this.sticky = sticky;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof SubscriberMethod) {
            checkMethodString();
            SubscriberMethod otherSubscriberMethod = (SubscriberMethod) other;
            otherSubscriberMethod.checkMethodString();
            // Don't use method.equals because of http://code.google.com/p/android/issues/detail?id=7811#c6
            return methodString.equals(otherSubscriberMethod.methodString);
        } else {
            return false;
        }
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            StringBuilder builder = new StringBuilder(64);
            builder.append(method.getDeclaringClass().getName());
            builder.append('#').append(method.getName());
            builder.append('(').append(eventType.getName());
            methodString = builder.toString();
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }
}
