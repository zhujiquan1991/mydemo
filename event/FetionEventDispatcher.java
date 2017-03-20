package com.feinno.emshop.api.business.event;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 飞信客户端事件派发器
 * Created by mac on 17/1/19.
 * @author zhujiquan
 */
public class FetionEventDispatcher {

    /**数据库事务内的监听器*/
    private static List<FetionEventListener> innerListeners = new CopyOnWriteArrayList<FetionEventListener>();

    /**数据库事务外的监听器,将采用异步线程派发事件*/
    private static List<FetionEventListener> outerListeners = new CopyOnWriteArrayList<FetionEventListener>();

    public static void addInnerListener(FetionEventListener listener) {
        if (listener == null) {
            throw new NullPointerException("Parameter listener was null.");
        }
        if (innerListeners.contains(listener)) {
            innerListeners.remove(listener);
        }
        innerListeners.add(listener);
    }

    public static void addOuterListener(FetionEventListener listener) {
        if (listener == null) {
            throw new NullPointerException("Parameter listener was null.");
        }
        if (outerListeners.contains(listener)) {
            outerListeners.remove(listener);
        }
        outerListeners.add(listener);
    }

    public static boolean removeInnerListener(FetionEventListener listener) {
        return innerListeners.remove(listener);
    }

    public static boolean removeOuterListener(FetionEventListener listener) {
        return outerListeners.remove(listener);
    }

    public static void notifyShiWuPackdownloadEvent(Map<String, Object> bundlemap) throws Exception {
        for (FetionEventListener listener : innerListeners) {
            listener.pack_download(bundlemap);
        }
    }

    /**
     * 异步方法
     * @param bundlemap
     * @throws Exception
     */
    public static void notifyPackdownloadEvent(final Map<String, Object> bundlemap) throws Exception {
        notifyShiWuPackdownloadEvent(bundlemap);
        new Thread()
        {
            @Override
            public void run() {
                for (FetionEventListener listener : outerListeners) {
                    try {
                        listener.pack_download(bundlemap);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
