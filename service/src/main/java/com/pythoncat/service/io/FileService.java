package com.pythoncat.service.io;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.pythoncat.service.IOInterface;
import com.pythoncat.service.callback.IOCallback;
import com.pythoncat.service.utils.FileCopy;

import java.io.IOException;

public class FileService extends Service {
    public FileService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteBinder();
    }

    class RemoteBinder extends IOInterface.Stub {

        @Override
        public void copy(String src, String dest, IOCallback call) throws RemoteException {
            try {
                FileCopy.copy(src, dest, call);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
