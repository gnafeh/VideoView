package com.emdoor.videoview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends Activity {
    private VideoView videoView;

    private String SD_PATH = Environment.getExternalStorageDirectory().getPath();
//    private String FOLDER_PATH = "videopath";
    private String FOLDER_PATH = "Movies";
    private String VIDEO_NAME = "advideo.wmv";
    //FULL_PATH = storage/emulated/0/videopath/advideo.wmv
    private String FULL_PATH = SD_PATH + "/" + FOLDER_PATH + "/" + VIDEO_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        showView();
    }


    private void initView() {
        videoView = findViewById(R.id.id_video_view);
    }
    private void showView() {
       if(isFolderExist(FULL_PATH)){
           runVideoPlay();
       }else{
           videoView.setVisibility(View.INVISIBLE);
       }
    }
    private boolean isFolderExist(String path) {
        File file = new File(path);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
    private void runVideoPlay (){

        videoView.setVideoPath(FULL_PATH);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(FULL_PATH);
                videoView.start();
            }
        });
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void showBottomUIMenu(){
        //恢复普通状态
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SCREEN_STATE_OFF;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
