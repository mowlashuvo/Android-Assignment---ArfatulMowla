package com.example.assignmentarfatulmowlashuvo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assignmentarfatulmowlashuvo.R;
import com.example.assignmentarfatulmowlashuvo.db.DbAuthor;
import com.example.assignmentarfatulmowlashuvo.db.DbModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.assignmentarfatulmowlashuvo.activity.MainActivity.myDatabase;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    DbModel modelBlog;
    TextView title, description, toolbarTitle, categoryTV, authorName, authorProfession;
    ImageView coverPhoto;
    ImageButton back;
    FloatingActionButton floatingActionButton;
    CircleImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        modelBlog = getIntent().getParcelableExtra("modelBlog");
        initView();
        getBlogCategory();
        getAuthorInfo();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        back = findViewById(R.id.id_back_toolbar);
        back.setOnClickListener(this);
        toolbarTitle = findViewById(R.id.toolbar_text);
        toolbarTitle.setText("Detail");
        title = findViewById(R.id.title);
        title.setText(modelBlog.getTitle());
        description = findViewById(R.id.description);
        description.setText(modelBlog.getDescription());
        coverPhoto = findViewById(R.id.coverPhoto);
        categoryTV = findViewById(R.id.categoryTV);
        Glide.with(this).load(modelBlog.getCoverPhoto()).into(coverPhoto);
        avatar = findViewById(R.id.avatar);
        authorName = findViewById(R.id.authorName);
        authorProfession = findViewById(R.id.authorProfession);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
    }

    private void getBlogCategory() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < modelBlog.getCategories().size(); i++){
            stringBuilder.append(modelBlog.getCategories().get(i));

            if (i != modelBlog.getCategories().size() - 1) {
                stringBuilder.append(", ");
            }
        }
        categoryTV.setText(stringBuilder.toString());

    }

    private void getAuthorInfo() {
        List<DbAuthor> dbAuthor = myDatabase.room().findAuthor(modelBlog.getAuthorId());
        for (int i=0; i<dbAuthor.size();i++) {
            authorName.setText(dbAuthor.get(i).getName());
            authorProfession.setText(dbAuthor.get(i).getProfession());
            Glide.with(this).load(dbAuthor.get(i).getAvatar()).into(avatar);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_back_toolbar:
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
                break;
            case R.id.floatingActionButton:
                Intent intent = new Intent(DetailActivity.this,CreateBlogActivity.class);
                intent.putExtra("To","Edit");
                intent.putExtra("modelBlog",modelBlog);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(DetailActivity.this,MainActivity.class));
    }
}