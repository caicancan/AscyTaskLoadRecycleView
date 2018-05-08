package com.ffpy.demo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 异步图片加载
 */
public abstract class MyImageLoader{
    public static final int WHAT = 98791981;                    //msg.what
    private final int CACHE_PART = 8;                           //把可用内存的 1/CACHE_PART 用于图片缓存

    private final Deque<ImageBean> deque = new ArrayDeque<>();  //等待队列
    private Handler handler;                                    //UI线程的Handler
    private Type loadType = Type.LIFO;                          //加载方式，默认为后进先出
    private LruCache<String, Bitmap> imgCache;                  //图片缓存
    private final Thread mThread = new Thread(){                //加载线程
        @Override
        public void run() {
            super.run();
            while (true){
                while(!deque.isEmpty()){
                    ImageBean bean;
                    synchronized (deque) {
                        //先进先出，取出队列的尾部
                        if (Type.FIFO == loadType){
                            bean = deque.removeLast();
                        }
                        //后进先出，取出队列的首部
                        else {
                            bean = deque.removeFirst();
                        }
                    }
                    //加载Bitmap
                    Bitmap bitmap = loadBitmap(bean);

                    //通知UI线程更新图片
                    if (null != bitmap) {
                        Message msg = new Message();
                        msg.what = WHAT;
                        bean.bitmap = bitmap;
                        msg.obj = bean;
                        handler.sendMessage(msg);
                    }
                }

                //没有任务则让线程休眠
                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * @param handler UI线程的Handler
     */
    public MyImageLoader(Handler handler){
        this.handler = handler;
        //设置图片缓存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / CACHE_PART;
        imgCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

        //开始加载线程
        mThread.start();
    }

    /**
     * 添加任务
     */
    public void add(ImageBean bean){
        Bitmap bitmap = getBitmapFromCache(bean.url);
        if (null != bitmap){
            Message msg = new Message();
            msg.what = WHAT;
            bean.bitmap = bitmap;
            msg.obj = bean;
            handler.sendMessage(msg);
        }

        synchronized (deque){
            deque.addFirst(bean);
        }

        if (Thread.State.WAITING == mThread.getState()){
            synchronized (mThread) {
                mThread.notify();
            }
        }
    }

    /**
     * 设置加载方式
     * @param type
     */
    public void setLoadType(Type type){
        if (null != type) {
            this.loadType = type;
        }
    }

    /**
     * 添加到缓存
     */
    public void addBitmapToCache(String key, Bitmap bitmap){
        if (null != key && null != bitmap){
            imgCache.put(key, bitmap);
        }
    }

    /**
     * 从缓存中取出
     */
    public Bitmap getBitmapFromCache(String key){
        return imgCache.get(key);
    }

    /**
     * 加载Bitmap的方法
     */
    public abstract Bitmap loadBitmap(ImageBean bean);

    public static class ImageBean{
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public ImageBean(ImageView imageView, String url) {
            this.imageView = imageView;
            this.url = url;
        }

    }

    public enum Type{
        FIFO,   //先进先出
        LIFO    //后进先出
    }
}
