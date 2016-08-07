// DownloadCallback.aidl
package com.pythoncat.service.callback;

// Declare any non-default types here with import statements
import com.pythoncat.service.domain.Download;


interface NetCallback {

    void progress(in Download down);
}
