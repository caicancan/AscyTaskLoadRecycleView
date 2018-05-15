package com.ffpy.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class HelloService extends Service {

    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        //aidl文件里面提供的方法
        @Override
        public int add(int arg1, int arg2) throws RemoteException {
            //具体是怎么实现的
                         return arg1 + arg2;
                    }

        @Override
        public void show() throws RemoteException {
            Log.i("ccc","执行服务端的aidl");
        }

        @Override
        public Beauty getBeauty() throws RemoteException {
            Beauty beauty = new Beauty();
            beauty.setAge(20);
            beauty.setName("张三丰");
            beauty.setSex("男");
            return beauty;
        }


    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mStub;
    }
}
