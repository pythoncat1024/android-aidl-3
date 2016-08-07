package com.pythoncat.ipcorservice3.bean;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class Download {

    public long progress;
    public long total;
    public boolean done;

    public Download(long progress, long total,boolean done) {
        this.progress = progress;
        this.done = done;
        this.total = total;
    }

    public Download() {
    }

    @Override
    public String toString() {
        return "Download{" +
                "progress=" + progress +
                ", total=" + total +
                ", done=" + done +
                '}';
    }
}
