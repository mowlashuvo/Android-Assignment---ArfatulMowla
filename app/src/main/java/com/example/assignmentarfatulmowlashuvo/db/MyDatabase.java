package com.example.assignmentarfatulmowlashuvo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {DbModel.class,DbAuthor.class},version = 1,exportSchema = true)
@TypeConverters(DataConverter.class)
public abstract class MyDatabase extends RoomDatabase
{

    public abstract Room room();
}