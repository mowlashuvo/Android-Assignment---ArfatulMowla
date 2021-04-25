package com.example.assignmentarfatulmowlashuvo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.assignmentarfatulmowlashuvo.Adapter;
import com.example.assignmentarfatulmowlashuvo.R;
import com.example.assignmentarfatulmowlashuvo.db.DbAuthor;
import com.example.assignmentarfatulmowlashuvo.db.DbModel;
import com.example.assignmentarfatulmowlashuvo.db.MyDatabase;
import com.example.assignmentarfatulmowlashuvo.model.Author;
import com.example.assignmentarfatulmowlashuvo.model.Blog;
import com.example.assignmentarfatulmowlashuvo.model.BlogModel;
import com.example.assignmentarfatulmowlashuvo.networking.APIClient;
import com.example.assignmentarfatulmowlashuvo.networking.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    private final String TAG = "xyz";
    public static MyDatabase myDatabase;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = Room.databaseBuilder(this, MyDatabase.class, "blogdb").allowMainThreadQueries().build();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        initView();
        retrofitCall();
        setAdapter();
        createBlog();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void retrofitCall() {
        /**
         GET List Blog
         **/
        Call<BlogModel> call = apiInterface.doGetListBlog();
        call.enqueue(new Callback<BlogModel>() {
            @Override
            public void onResponse(Call<BlogModel> call, Response<BlogModel> response) {

                BlogModel blogModel = response.body();

                List<Blog> blogList = blogModel.getBlogs();
                List<String> categoriesList = new ArrayList<>();
                DbAuthor dbAuthor = new DbAuthor();

                for (int i = 0; i < blogList.size(); i++) {
                    categoriesList.clear();

                    Blog blog = blogList.get(i);

                    int blogId = blog.getId();
                    String title = blog.getTitle();
                    String description = blog.getDescription();
                    String coverPhoto = blog.getCoverPhoto();

                    List<String> categories = blog.getCategories();

                    for (int j = 0; j < categories.size(); j++) {
                        Object cat = categories.get(j);
                        categoriesList.add(cat + "");
                    }

                    Author author = blog.getAuthor();

                    int authorId = author.getId();
                    String name = author.getName();
                    String avatar = author.getAvatar();
                    String profession = author.getProfession();

                    dbAuthor.setId(authorId);
                    dbAuthor.setName(name);
                    dbAuthor.setAvatar(avatar);
                    dbAuthor.setProfession(profession);

                    DbModel blogDb = new DbModel(blogId, authorId, title, description, coverPhoto, categoriesList);
                    myDatabase.room().addBlog(blogDb);
                    myDatabase.room().addAuthor(dbAuthor);
                    setAdapter();
                }

            }

            @Override
            public void onFailure(Call<BlogModel> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void setAdapter() {
        List<DbModel> dbModelList = myDatabase.room().getBlogs();
        Adapter adapter = new Adapter(MainActivity.this, dbModelList);
        recyclerView.setAdapter(adapter);
    }

    private void createBlog() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateBlogActivity.class);
                intent.putExtra("To", "Create");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}