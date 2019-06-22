package com.barebones.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barebones.R;
import com.barebones.activity.ChannelActivity;
import com.barebones.adapter.CommentsRecyclerAdapter;
import com.barebones.constants.Constants;
import com.barebones.models.Comments;
import com.barebones.models.Video;
import com.barebones.retrofit.ApiInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailFragment extends Fragment {

    private ImageView mThumbImageView;
    private TextView mVideoTitleView;
    private TextView mVideoChannelView;
    private TextView mVideoViewsView;
    private Button mVideoLikeView;
    private boolean isVideoLiked = false;
    private Button mSubscribeButton;
    private EditText mCommentEditText;
    private Button mCommentButton;


    private Video mVideo;

    private RecyclerView mCommentsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_video_detail, container, false);
        getViewsReferences(parentView);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVideoResponse();
        getCommentsResponse();
    }

    private void getViewsReferences(View view) {
        mVideoTitleView = view.findViewById(R.id.video_title_text_view);
        mVideoChannelView = view.findViewById(R.id.video_channel_text_view);
        mVideoViewsView = view.findViewById(R.id.video_likes_text_view);
        mSubscribeButton = view.findViewById(R.id.subscribe_button_view);
        mVideoLikeView = view.findViewById(R.id.video_like_text_view);
        mCommentsRecyclerView = view.findViewById(R.id.comments_recycler_view);
        mThumbImageView = view.findViewById(R.id.video_thumbnail_image_view);
        mCommentEditText = view.findViewById(R.id.comment_edit_text);
        mCommentButton = view.findViewById(R.id.comment_button);

        mThumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Play Video
            }
        });
        mSubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubscribeVideo();
            }
        });
        mVideoLikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLikeButton();
            }
        });
        mVideoChannelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChannelScreen();
            }
        });
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCommentToApi();
            }
        });
    }

    private void postCommentToApi() {
        if (mCommentEditText.getText() != null) {
            final String comment = mCommentEditText.getText().toString();
            String user = "Test";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<Comments> call = apiInterface.postVideoComments(user , comment);
            call.enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    if(response.isSuccessful()){
                        Comments comments = response.body();
                        if (comments != null) {

                        }
                    } else  {
                        Toast.makeText(getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Comments> call, Throwable throwable) {
                    Toast.makeText(getContext(),throwable.toString(),Toast.LENGTH_SHORT).show(); // ALL NETWORK ERROR HERE
                }
            });

        }
    }

    private void launchChannelScreen() {
        if (mVideo == null)
            //Display Error Message
            return;

        Intent channelIntent = new Intent(getActivity() , ChannelActivity.class);
        channelIntent.putExtra(Constants.VIDEO_RESPONSE , mVideo);
        getActivity().startActivity(channelIntent);
    }

    private void handleSubscribeVideo() {

        if (mVideo == null) {
           return;
        }
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        boolean isChannelSubscribed = sharedPref.getBoolean(mVideo.getChannel(), false);
        CharSequence subscribeText = isChannelSubscribed ? getText(R.string.unsubscribe) : getText(R.string.subscribe);
        mSubscribeButton.setText(subscribeText);
        isChannelSubscribed = !isChannelSubscribed;

        editor.putBoolean(mVideo.getChannel(), isChannelSubscribed);
        editor.commit();
    }

    private void getVideoResponse() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Video> call = apiInterface.getVideoDetails();
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if(response.isSuccessful()){
                    // have your all data
                    mVideo = response.body();
                    populateVideoDetails();

                }else  {
                    Toast.makeText(getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable throwable) {
                Toast.makeText(getContext(),throwable.toString(),Toast.LENGTH_SHORT).show(); // ALL NETWORK ERROR HERE

            }
        });

    }

    private void getCommentsResponse() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<List<Comments>> call = apiInterface.getVideoComments();
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if(response.isSuccessful()){
                    // have your all data
                    List<Comments> comments = response.body();
                    populateCommentsPage(comments);

                }else  {
                    Toast.makeText(getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable throwable) {
                Toast.makeText(getContext(),throwable.toString(),Toast.LENGTH_SHORT).show(); // ALL NETWORK ERROR HERE
            }
        });

    }

    private void populateCommentsPage(List<Comments> commentsList) {
        if (commentsList != null && commentsList.size() > 0) {
            mCommentsRecyclerView.setAdapter(new CommentsRecyclerAdapter(commentsList));
            mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    private void populateVideoDetails() {
        if (mVideo != null) {
            Uri uri = Uri.parse(mVideo.getImage());
            //mThumbImageView.setImageURI(uri);
            mVideoTitleView.setText(mVideoTitleView.getText()+ mVideo.getDescription());
            mVideoChannelView.setText(mVideoChannelView.getText() + mVideo.getChannel());
            mVideoViewsView.setText(mVideoViewsView.getText() + mVideo.getViews());
            mSubscribeButton.setVisibility(View.VISIBLE);
        }
    }

    private void handleLikeButton() {
        CharSequence likeText = isVideoLiked ? getText(R.string.video_unlike) : getText(R.string.video_like);
        mVideoLikeView.setText(likeText);
        isVideoLiked = !isVideoLiked;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        handleOrientationVideo(newConfig.orientation);
    }

    private void handleOrientationVideo(int orientation) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mThumbImageView.getLayoutParams();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            mThumbImageView.setLayoutParams(params);
        } else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.width = metrics.widthPixels;
            params.height = (int)(200*metrics.density);
            mThumbImageView.setLayoutParams(params);
        }
    }
}
