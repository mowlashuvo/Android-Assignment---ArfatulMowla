package com.example.assignmentarfatulmowlashuvo.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "blogs",foreignKeys = @ForeignKey(entity = DbModel.class,
        parentColumns = "id",
        childColumns = "author_id",
        onDelete = CASCADE))
public class DbModel implements Parcelable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "author_id")
    private int authorId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "cover_photo")
    private String coverPhoto;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "categories")
    private List<String> categories;

    public DbModel(int id, int authorId, String title, String description, String coverPhoto, List<String> categories) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.coverPhoto = coverPhoto;
        this.categories = categories;
    }

    protected DbModel(Parcel in) {
        id = in.readInt();
        authorId = in.readInt();
        title = in.readString();
        description = in.readString();
        coverPhoto = in.readString();
        categories = in.createStringArrayList();
    }

    public static final Creator<DbModel> CREATOR = new Creator<DbModel>() {
        @Override
        public DbModel createFromParcel(Parcel in) {
            return new DbModel(in);
        }

        @Override
        public DbModel[] newArray(int size) {
            return new DbModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<String> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
            return categories;
        }
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(authorId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(coverPhoto);
        dest.writeStringList(categories);
    }
}