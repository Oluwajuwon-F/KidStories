package com.project.android_kidstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android_kidstories.Api.Api;
import com.project.android_kidstories.Api.Responses.story.StoryBaseResponse;
import com.project.android_kidstories.Api.RetrofitClient;
import com.project.android_kidstories.Model.Story;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author .: Oluwajuwon Fawole
 * @created : 11/10/19
 */

public class SingleStoryActivity extends AppCompatActivity {

    private ImageView story_pic, like_btn;
    private TextView story_author , story_content;
    int story_id = 2;

    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_story);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDoalog = new ProgressDialog(SingleStoryActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        story_author = findViewById(R.id.author_name);
        story_content = findViewById(R.id.story_content);
        story_pic = findViewById(R.id.story_pic);
        like_btn = findViewById(R.id.like_button);

        int story_id = getIntent().getIntExtra("story_id", 0);

        Api service = RetrofitClient.getInstance().create(Api.class);
        Call<StoryBaseResponse> story = service.getStory(story_id);

        story.enqueue(new Callback<StoryBaseResponse>() {
            @Override
            public void onResponse(Call<StoryBaseResponse> call, Response<StoryBaseResponse> response) {
                progressDoalog.dismiss();
                Log.i("apple", response.message());
                try {
                    Story currentStory = response.body().getData();
                    story_author.setText(currentStory.getAuthor());
                    story_content.setText(currentStory.getBody());

                    getSupportActionBar().setTitle(currentStory.getTitle());
                }catch (Exception e){
                    Toast.makeText(SingleStoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<StoryBaseResponse> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(SingleStoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
