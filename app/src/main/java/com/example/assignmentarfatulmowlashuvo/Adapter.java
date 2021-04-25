package com.example.assignmentarfatulmowlashuvo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignmentarfatulmowlashuvo.activity.DetailActivity;
import com.example.assignmentarfatulmowlashuvo.db.DbModel;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final Activity activity;
    private final List<DbModel> arrayList;

    public Adapter(Activity activity, List<DbModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter.ViewHolder holder, int position) {
        DbModel modelBlog = arrayList.get(position);

        Glide.with(activity).load(modelBlog.getCoverPhoto()).into(holder.coverPhoto);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("modelBlog", modelBlog);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverPhoto;
        LinearLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverPhoto = itemView.findViewById(R.id.coverPhoto);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
