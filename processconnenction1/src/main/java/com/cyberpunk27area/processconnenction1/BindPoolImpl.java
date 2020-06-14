package com.cyberpunk27area.processconnenction1;

import android.os.IBinder;
import android.os.RemoteException;

public class BindPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderPool.BINDER_BOOKS:
                binder = BooksBinderImpl.getInstance();
                break;
            case BinderPool.BINDER_BITMAP:
                binder = BitmapBinderImpl.getInstance();
                break;
        }
        return binder;
    }
}
