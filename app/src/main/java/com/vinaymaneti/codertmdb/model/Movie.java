package com.vinaymaneti.codertmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.vinaymaneti.codertmdb.Util.Constants;

import java.util.Arrays;

/**
 * Created by vinay on 14/10/16.
 */

public class Movie implements Parcelable {
    @SerializedName("poster_path")
    private String posterPath;// portrait mode
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("overview")
    private String overview; // description of the film
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private String[] genreIds;
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle; // film name
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath; // landscape mode
    @SerializedName("popularity")
    private double popularity; // how famous the movie is
    @SerializedName("vote_count")
    private int voteCount; // like number of likes
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage; // Rating

    public Movie(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        genreIds = in.createStringArray();
        id = in.readInt();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {

    }

    public String getPosterPath() {
        return Constants.STATIC_IMAGE_BASE_URL + posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String[] getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return Constants.STATIC_IMAGE_BASE_URL + backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }


    public float getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreIds=" + Arrays.toString(genreIds) +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity='" + popularity + '\'' +
                ", voteCount='" + voteCount + '\'' +
                ", video=" + video +
                ", voteAverage='" + voteAverage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeStringArray(genreIds);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(voteAverage);
    }
}
