package com.cyberpunk27area.processconnenction1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BinderPoolService extends Service {
    private BindPoolImpl binder = new BindPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
