package com.vinaymaneti.codertmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay on 16/10/16.
 */
public class Youtube implements Parcelable{
    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private String size;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;

    protected Youtube(Parcel in) {
        name = in.readString();
        size = in.readString();
        source = in.readString();
        type = in.readString();
    }

    public static final Creator<Youtube> CREATOR = new Creator<Youtube>() {
        @Override
        public Youtube createFromParcel(Parcel in) {
            return new Youtube(in);
        }

        @Override
        public Youtube[] newArray(int size) {
            return new Youtube[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(source);
        dest.writeString(type);
    }
}
