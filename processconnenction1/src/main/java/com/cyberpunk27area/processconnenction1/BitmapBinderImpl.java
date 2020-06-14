package com.cyberpunk27area.processconnenction1;

import android.graphics.Bitmap;
import android.os.RemoteException;

public class BitmapBinderImpl extends IBitmapBinder.Stub {

    private Bitmap mBitmap;

    private BitmapBinderImpl() {
    }

    public static BitmapBinderImpl INSTANCE = null;

    public static BitmapBinderImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (BitmapBinderImpl.class) {
                INSTANCE = new BitmapBinderImpl();
            }
        }
        return INSTANCE;
    }


    @Override
    public void setBitmap(Bitmap bitmap) throws RemoteException {
        mBitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() throws RemoteException {
        return mBitmap;
    }
}
