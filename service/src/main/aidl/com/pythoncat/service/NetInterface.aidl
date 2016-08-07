// NetInterface.aidl
package com.pythoncat.service;

// Declare any non-default types here with import statements
import com.pythoncat.service.domain.Download;
import com.pythoncat.service.callback.NetCallback;

interface NetInterface {

  void download(in String url,in NetCallback netCall);
}
