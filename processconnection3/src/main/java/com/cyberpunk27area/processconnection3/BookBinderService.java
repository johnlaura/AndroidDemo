package com.cyberpunk27area.processconnection3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BookBinderService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BookBinderImpl();
    }
}
