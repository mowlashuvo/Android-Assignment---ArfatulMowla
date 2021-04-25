package com.example.assignmentarfatulmowlashuvo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmentarfatulmowlashuvo.db.DbModel;

import java.util.List;

@Dao
public interface Room {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addBlog(DbModel dbModel);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addAuthor(DbAuthor dbAuthor);

    @Query("select * from blogs")
    public List<DbModel> getBlogs();

    @Query("select * from author")
    public List<DbAuthor> getAuthor();

    @Query("SELECT * FROM author WHERE id=:author_id")
    List<DbAuthor> findAuthor(final int author_id);

    @Delete
    public void deleteBlog(DbModel dbModel);

    @Update
    public void updateBlog(DbModel dbModel);


}