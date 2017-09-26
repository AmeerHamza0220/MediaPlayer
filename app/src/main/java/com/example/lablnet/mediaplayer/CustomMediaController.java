package com.example.lablnet.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by lablnet on 9/15/2017.
 */

public class CustomMediaController extends MediaController {
    public CustomMediaController(Context context) {
        super(context);
        mContext = context;
    }
    public interface OnMediaControllerInteractionListener {
        void onRequestFullScreen();
        void OnCancel();
    }
    Context mContext;
    private OnMediaControllerInteractionListener mListener;
    public void setListener(OnMediaControllerInteractionListener listener) {
            mListener = listener;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        frameParams.gravity = Gravity.RIGHT|Gravity.TOP;
        FrameLayout.LayoutParams frameParamsForVolume = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        SeekBar seekBar=(SeekBar)LayoutInflater.from(mContext).inflate(R.layout.seek_bar_for_volume,null);
        final AudioManager audioManager = (AudioManager)mContext. getSystemService(AUDIO_SERVICE);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        final ToggleButton fullscreenButton = (ToggleButton) LayoutInflater.from(mContext)
                .inflate(R.layout.fullscreen_button, null);
        addView(fullscreenButton, frameParams);
        addView(seekBar,frameParamsForVolume);
       fullscreenButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(mListener!=null) {
                   if (isChecked) {
                       mListener.onRequestFullScreen();
                   } else {
                       mListener.OnCancel();
                   }
               }
           }
       });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
