// IOCallback.aidl
package com.pythoncat.service.callback;
import com.pythoncat.service.domain.FileInfo;
// Declare any non-default types here with import statements

interface IOCallback {

    void callback(in FileInfo fo);
}
