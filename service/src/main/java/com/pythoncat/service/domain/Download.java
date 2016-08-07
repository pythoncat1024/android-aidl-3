package com.pythoncat.service.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class Download implements Parcelable {

    public long progress;
    public long total;
    public boolean done;

    public Download(long progress, long total, boolean done) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.progress);
        dest.writeLong(this.total);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
    }

    protected Download(Parcel in) {
        this.progress = in.readLong();
        this.total = in.readLong();
        this.done = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Download> CREATOR = new Parcelable.Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel source) {
            return new Download(source);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
}
