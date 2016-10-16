package com.vinaymaneti.codertmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay on 16/10/16.
 */

public class Trailers implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("quicktime")
    private String[] quicktime;
    @SerializedName("youtube")
    private Youtube[] youtube;

    protected Trailers(Parcel in) {
        id = in.readInt();
        quicktime = in.createStringArray();
        youtube = in.createTypedArray(Youtube.CREATOR);
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };

    public int getId() {
        return id;
    }

    public String[] getQuicktime() {
        return quicktime;
    }

    public Youtube[] getYoutube() {
        return youtube;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeStringArray(quicktime);
        dest.writeTypedArray(youtube, flags);
    }
}
