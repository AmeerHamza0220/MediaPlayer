package com.example.lablnet.mediaplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class Play_Video_Activity extends AppCompatActivity {
    VideoView videoView;
    RelativeLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__video_);
        layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        videoView = (VideoView) findViewById(R.id.videoView);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("Path");// get path of video
        videoView.setVideoURI(Uri.parse(uri));
        CustomMediaController mediaController = new CustomMediaController(this);// set media control button
        videoView.setMediaController(mediaController);
        mediaController.show();
        videoView.start();// start video
        mediaController.setListener(new CustomMediaController.OnMediaControllerInteractionListener() {
            @Override
            public void onRequestFullScreen() {
                setFullScreen();
            }

            @Override
            public void OnCancel() {
                exitFullScreen();
            }

        });
    }
    public void setFullScreen(){
        videoView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);
    }
    public void exitFullScreen(){
        videoView.setSystemUiVisibility(0);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
        videoView.setLayoutParams(layoutParams);
    }
}