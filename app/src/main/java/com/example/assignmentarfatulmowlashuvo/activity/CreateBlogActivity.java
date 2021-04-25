package com.example.assignmentarfatulmowlashuvo.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmentarfatulmowlashuvo.R;
import com.example.assignmentarfatulmowlashuvo.db.DbModel;
import com.example.assignmentarfatulmowlashuvo.db.MyDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateBlogActivity extends AppCompatActivity implements View.OnClickListener {
    String to;
    DbModel modelBlog;
    TextInputEditText et_title,et_description;
    Button saveBtn;
    public static MyDatabase myDatabase;
    private String TAG = "abc";

    ImageButton back;
    TextView toolbarTitle;

    TextView categoryListTV;
    boolean[] selectedCategory;
    ArrayList<Integer> categoryList = new ArrayList<>();
    String[] categoryArray = {"Business", "Lifestyle", "Entertainment", "Productivity"};

    List<String> dbCategoriesList = new ArrayList<>();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);

        initView();

        myDatabase = Room.databaseBuilder(CreateBlogActivity.this, MyDatabase.class, "blogdb").allowMainThreadQueries().build();

        to = getIntent().getStringExtra("To");
        if (to.equals("Edit")) {
            toolbarTitle.setText("Edit Blog");
            modelBlog = getIntent().getParcelableExtra("modelBlog");
            etHint();
        }else {
            toolbarTitle.setText("New Blog");
        }

        categoryListMethod();

    }

    private void initView() {
        back = findViewById(R.id.id_back_toolbar);
        back.setOnClickListener(this);
        toolbarTitle = findViewById(R.id.toolbar_text);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        categoryListTV = findViewById(R.id.categoryListTV);

    }

    private void etHint() {
        et_title.setText(modelBlog.getTitle());
        et_description.setText(modelBlog.getDescription());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                List<DbModel> dbModelList = myDatabase.room().getBlogs();
                int lastBlogID = dbModelList.get(dbModelList.size()-1).getId();

                String title = et_title.getText().toString();
                String description = et_description.getText().toString();


                if (to.equals("Edit")){
                    DbModel blogDbEdit = new DbModel(modelBlog.getId(), modelBlog.getAuthorId(), title, description, modelBlog.getCoverPhoto(), dbCategoriesList);
                    myDatabase.room().updateBlog(blogDbEdit);
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateBlogActivity.this,DetailActivity.class);
                    intent.putExtra("modelBlog", blogDbEdit);
                    startActivity(intent);
                }else {
                    DbModel blogDb = new DbModel(lastBlogID+1,1, title, description, "https://i.picsum.photos/id/579/200/300.jpg?hmac=9MD8EV4Jl9EqKLkTj5kyNdBUKQWyHk2m4pE4UCBGc8Q", dbCategoriesList);
                    myDatabase.room().addBlog(blogDb);
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateBlogActivity.this,MainActivity.class));
                }
                break;

            case R.id.id_back_toolbar:
                finish();
                break;
        }
    }

    public void categoryListMethod() {
        selectedCategory = new boolean[categoryArray.length];
        if (to.equals("Edit")){
            for (int i=0; i<modelBlog.getCategories().size(); i++) {
                for (int j=0; j<categoryArray.length; j++) {
                    if (modelBlog.getCategories().get(i).equals(categoryArray[j])) {
                        selectedCategory[j] = true;
                        categoryList.add(j);
                    }
                }

            }
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < categoryList.size(); i++) {
                stringBuilder.append(categoryArray[categoryList.get(i)]);
                if (i != categoryList.size() - 1) {
                    stringBuilder.append(", ");
                }

            }
            categoryListTV.setText(stringBuilder.toString());

        }

        categoryListTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < selectedCategory.length; i++) {
                    selectedCategory[i] = false;
                    categoryList.clear();
                    categoryListTV.setText("");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateBlogActivity.this);
                builder.setTitle("Select Category");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(categoryArray, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            categoryList.add(which);
                            Collections.sort(categoryList);
                        } else {
                            categoryList.remove(which);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < categoryList.size(); i++) {
                            Log.d(TAG, "onClick: "+categoryList.get(i));
                            stringBuilder.append(categoryArray[categoryList.get(i)]);
                            dbCategoriesList.add(categoryArray[categoryList.get(i)] + "");
                            if (i != categoryList.size() - 1) {
                                stringBuilder.append(", ");
                            }

                        }
                        categoryListTV.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

}