package com.project.android_kidstories.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.android_kidstories.Api.Api;
import com.project.android_kidstories.Api.Responses.story.StoryAllResponse;
import com.project.android_kidstories.Api.RetrofitClient;
import com.project.android_kidstories.R;
import com.project.android_kidstories.adapters.RecyclerStoriesAdapter;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author .: Oluwajuwon Fawole
 * @created : 12/10/19
 */
public class NewStoriesFragment extends Fragment {


    private RecyclerStoriesAdapter adapter;
    ProgressDialog progressDoalog;
    RecyclerView recyclerView;


    public static NewStoriesFragment newInstance(){return new NewStoriesFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newstories,container,false);
        ButterKnife.bind(this,v);


        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        Api service = RetrofitClient.getInstance().create(Api.class);
        Call<StoryAllResponse> categories = service.getAllStories();
        Log.i("apple", "Size: "+categories.isExecuted());

        categories.enqueue(new Callback<StoryAllResponse>() {
            @Override
            public void onResponse(Call<StoryAllResponse> call, Response<StoryAllResponse> response) {
                //  generateCategoryList(response.body(),v);
                progressDoalog.dismiss();
                recyclerView = v.findViewById(R.id.recyclerView);

                try {
                    adapter = new RecyclerStoriesAdapter(getContext(), response.body());
                    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StoryAllResponse> call, Throwable t) {
                progressDoalog.dismiss();

                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
/*
    @Override
    public void onStoryClick() {
        startActivity(new Intent(requireActivity(), SingleStoryActivity.class));

    }*/

}
