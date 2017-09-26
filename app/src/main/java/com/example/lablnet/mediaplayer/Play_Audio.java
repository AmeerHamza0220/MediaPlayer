package com.example.lablnet.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.IOException;

public class Play_Audio extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__audio);
        Intent intent = getIntent();
        String path = intent.getStringExtra("Path");
        String title = intent.getStringExtra("Title");
        TextView txtTitle = (TextView) findViewById(R.id.txtName);
        txtTitle.setText(title);
        mediaPlayer = new MediaPlayer();
        /* if media id playing then first release then start new*/
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(this){
            @Override
            public void hide() {
               /* never hide mediaController
                * when it hide then again show
                * by using this you can't go back from this activity
                */
                super.show();
            }
        };
        try {
            mediaPlayer.setDataSource(this, parseUri(path));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*initialise mediaController*/
        final MediaController.MediaPlayerControl mediaPlayerControl = new MediaController.MediaPlayerControl() {
            @Override
            public void start() {
                mediaPlayer.start();
            }

            @Override
            public void pause() {
                mediaPlayer.pause();
            }

            @Override
            public int getDuration() {
                return mediaPlayer.getDuration();
            }

            @Override
            public int getCurrentPosition() {
                return mediaPlayer.getCurrentPosition();
            }

            @Override
            public void seekTo(int pos) {
                mediaPlayer.seekTo(pos);
            }

            @Override
            public boolean isPlaying() {
                return mediaPlayer.isPlaying();
            }

            @Override
            public int getBufferPercentage() {
                return 0;
            }

            @Override
            public boolean canPause() {
                return true;
            }

            @Override
            public boolean canSeekBackward() {
                return true;
            }

            @Override
            public boolean canSeekForward() {
                return true;
            }

            @Override
            public int getAudioSessionId() {
                return 0;
            }
        };
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.setMediaPlayer(mediaPlayerControl);
                mediaController.setAnchorView(findViewById(R.id.relative_layout));
                /*Use handler/thread to show media player*/
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaController.show();
                    }
                });
            }
        });
    }

    /*this method convert string to URI*/
    public Uri parseUri(String path) {
        return Uri.parse(path);
    }

}
