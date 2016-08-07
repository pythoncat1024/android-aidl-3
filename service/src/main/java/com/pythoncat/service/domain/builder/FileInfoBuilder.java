package com.pythoncat.service.domain.builder;

import com.pythoncat.service.domain.FileInfo;
import com.pythoncat.service.i.IOState;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class FileInfoBuilder {

    private static FileInfo mInfo;

    private static FileInfoBuilder b = new FileInfoBuilder();

    private FileInfoBuilder() {
        throw new RuntimeException("do not");
    }

    public static FileInfoBuilder build() {
        if (mInfo == null) {
            mInfo = new FileInfo();
        }
        return b;
    }

    /*
     public long progress;
    public long total;
    public String dest;
    public String src;
    public IOState state; // 1 开始，2 进行中，3 结束

     */
    public static FileInfoBuilder state(IOState state) {
        mInfo.state = state;
        return b;
    }

    public static FileInfoBuilder src(String src) {
        mInfo.src = src;
        return b;
    }

    public static FileInfoBuilder dest(String dest) {
        mInfo.dest = dest;
        return b;
    }

    public static FileInfoBuilder total(long total) {
        mInfo.total = total;
        return b;
    }

    public static FileInfoBuilder progress(long progress) {
        mInfo.progress = progress;
        return b;
    }

    public static FileInfo ok() {
        return mInfo;
    }

    public static FileInfo reset() {
        mInfo.src = null;
        mInfo.state = null;
        mInfo.dest = null;
        mInfo.progress = 0;
        mInfo.total = 0;
        return mInfo;
    }
}
