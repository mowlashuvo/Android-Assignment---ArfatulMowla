package com.example.assignmentarfatulmowlashuvo.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromCategoriesList(List<String> categories) {
        if (categories == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        String json = gson.toJson(categories, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<String> toCategoriesList(String categories) {
        if (categories == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> categoriesList = gson.fromJson(categories, type);
        return categoriesList;
    }
}