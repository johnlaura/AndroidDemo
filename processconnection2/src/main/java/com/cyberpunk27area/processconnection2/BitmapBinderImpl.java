package com.cyberpunk27area.processconnection2;

import android.graphics.Bitmap;
import android.os.RemoteException;

import com.cyberpunk27area.processconnenction1.IBitmapBinder;

public class BitmapBinderImpl extends IBitmapBinder.Stub {

    private Bitmap mBitmap;

    @Override
    public void setBitmap(Bitmap bitmap) throws RemoteException {
        mBitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() throws RemoteException {
        return mBitmap;
    }
}
