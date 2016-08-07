// IOInterface.aidl
package com.pythoncat.service;
// Declare any non-default types here with import statements
import com.pythoncat.service.domain.FileInfo;
import com.pythoncat.service.callback.IOCallback;

interface IOInterface {

    void copy(in String src,in String dest,in IOCallback call);
}
