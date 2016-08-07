package com.pythoncat.service.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class FileInfo implements Parcelable {

    public long progress;
    public long total;
    public String dest;
    public String src;
    public boolean finished;
    public boolean started;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.progress);
        dest.writeLong(this.total);
        dest.writeString(this.dest);
        dest.writeString(this.src);
        dest.writeByte(this.finished ? (byte) 1 : (byte) 0);
        dest.writeByte(this.started ? (byte) 1 : (byte) 0);
    }

    public FileInfo() {
    }

    protected FileInfo(Parcel in) {
        this.progress = in.readLong();
        this.total = in.readLong();
        this.dest = in.readString();
        this.src = in.readString();
        this.finished = in.readByte() != 0;
        this.started = in.readByte() != 0;
    }

    public static final Parcelable.Creator<FileInfo> CREATOR = new Parcelable.Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel source) {
            return new FileInfo(source);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };
}
