package com.pythoncat.service.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.pythoncat.service.i.IOState;

/**
 * Created by pythonCat on 2016/8/7.
 */
public class FileInfo implements Parcelable {

    public long progress;
    public long total;
    public String dest;
    public String src;
    public IOState state; // 1 开始，2 进行中，3 结束

    @Override
    public String toString() {
        return "FileInfo{" +
                "progress=" + progress +
                ", total=" + total +
                ", dest='" + dest + '\'' +
                ", src='" + src + '\'' +
                ", state=" + state +
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
        dest.writeString(this.dest);
        dest.writeString(this.src);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
    }

    public FileInfo() {
    }

    protected FileInfo(Parcel in) {
        this.progress = in.readLong();
        this.total = in.readLong();
        this.dest = in.readString();
        this.src = in.readString();
        int tmpState = in.readInt();
        this.state = tmpState == -1 ? null : IOState.values()[tmpState];
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
