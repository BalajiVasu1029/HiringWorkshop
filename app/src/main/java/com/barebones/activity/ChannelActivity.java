package com.barebones.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.barebones.R;
import com.barebones.constants.Constants;
import com.barebones.models.Video;

public class ChannelActivity extends AppCompatActivity {

    private TextView mChannelNameTextView;
    private TextView mChannelSubscribersTextView;
    private TextView mChannelOwnerTextView;
    private Button mSubscribeButton;
    private Video mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        getViewReferences();
        mVideo = getIntent().getParcelableExtra(Constants.VIDEO_RESPONSE);
        if (mVideo != null) {
            populateViews(mVideo);
        }

    }

    private void getViewReferences() {
        mChannelNameTextView = (TextView) findViewById(R.id.channel_name_text_view);
        mChannelSubscribersTextView = (TextView) findViewById(R.id.channel_subscribers_text_view);
        mChannelOwnerTextView = (TextView) findViewById(R.id.channel_owner_text_view);

        mSubscribeButton = (Button) findViewById(R.id.subscribe_button);
        mSubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeVideo();
            }
        });
    }

    private void subscribeVideo() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        boolean isChannelSubscribed = sharedPref.getBoolean(mVideo.getChannel(), false);
        CharSequence subscribeText = isChannelSubscribed ? getText(R.string.unsubscribe) : getText(R.string.subscribe);
        mSubscribeButton.setText(subscribeText);
        isChannelSubscribed = !isChannelSubscribed;

        editor.putBoolean(mVideo.getChannel(), isChannelSubscribed);
        editor.commit();
    }

    private void populateViews(Video video) {
        mChannelNameTextView.setText(video.getChannel());
        mChannelSubscribersTextView.setText(video.getChannelSubscribers());
        mChannelOwnerTextView.setText(video.getChannelOwner());
    }
}
