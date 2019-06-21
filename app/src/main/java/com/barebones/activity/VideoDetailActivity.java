package com.barebones.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.barebones.R;
import com.barebones.fragments.VideoDetailFragment;

public class VideoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        addVideoFragment();
    }

    private void addVideoFragment() {
        VideoDetailFragment videoFragment = new VideoDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.video_frame_layout , videoFragment);
        fragmentTransaction.commit();
    }
}
