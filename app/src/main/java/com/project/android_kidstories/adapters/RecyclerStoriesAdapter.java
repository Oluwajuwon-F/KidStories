package com.project.android_kidstories.adapters;


import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.android_kidstories.Api.Responses.story.StoryAllResponse;
import com.project.android_kidstories.Model.Story;
import com.project.android_kidstories.R;
import com.project.android_kidstories.SingleStoryActivity;

import java.util.List;

import io.reactivex.Single;

public class RecyclerStoriesAdapter extends RecyclerView.Adapter<RecyclerStoriesAdapter.CustomViewHolder>{


    private Context context;
    private StoryAllResponse storiesList;



    public RecyclerStoriesAdapter(Context context, StoryAllResponse storiesList) {
        this.context = context;
        this.storiesList = storiesList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        ImageView storyImage;
        TextView storyTitle;
        TextView authorName;
        TextView ageRange;
        TextView likes;
        TextView dislikes;


        CustomViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            storyImage = view.findViewById(R.id.story_image);
            storyTitle = view.findViewById(R.id.story_name);
            authorName = view.findViewById(R.id.story_author);
            ageRange = view.findViewById(R.id.story_age_range);
            likes = view.findViewById(R.id.story_likes);
            dislikes = view.findViewById(R.id.story_dislikes);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Glide.with(context).load(storiesList.getData().get(position).getImageUrl()).into(holder.storyImage);

        holder.storyTitle.setText(storiesList.getData().get(position).getTitle());
        holder.authorName.setText(storiesList.getData().get(position).getAuthor());
        holder.ageRange.setText(storiesList.getData().get(position).getAge());
        holder.likes.setText(storiesList.getData().get(position).getLikesCount()+"");
        holder.dislikes.setText(storiesList.getData().get(position).getDislikesCount()+"");


        holder.storyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int story_id = storiesList.getData().get(position).getId();
                Intent intent = new Intent(context, SingleStoryActivity.class);
                intent.putExtra("story_id", story_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storiesList.getData().size();
    }
}
