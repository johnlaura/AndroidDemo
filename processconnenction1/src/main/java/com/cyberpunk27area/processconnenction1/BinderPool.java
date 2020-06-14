package com.cyberpunk27area.processconnenction1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class BinderPool {

    public static final int BINDER_BOOKS = 1;
    public static final int BINDER_BITMAP = 2;
    private static BinderPool INSTANCE = null;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private IBinderPool mBinderPool;
    private Context mContext;

    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("abc","onServiceConnected");
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("abc","onServiceDisconnected");
        }
    };

    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectionService(mContext);

        }
    };

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectionService(mContext);
    }

    private synchronized void connectionService(Context context) {
        Intent service = new Intent(context, BinderPoolService.class);
        context.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder iBinder = null;
        if (mBinderPool != null) {
            try {
                iBinder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return iBinder;
    }

    public static BinderPool getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BinderPool.class) {
                INSTANCE = new BinderPool(context);
            }
        }
        return INSTANCE;
    }


}
