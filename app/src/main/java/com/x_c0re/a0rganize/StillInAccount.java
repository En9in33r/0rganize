package com.x_c0re.a0rganize;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class StillInAccount extends Service
{
    public StillInAccount()
    {

    }

    @Override
    public void onCreate()
    {
        
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
