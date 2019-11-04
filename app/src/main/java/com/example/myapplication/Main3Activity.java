package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.io.File;
import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";

    //레코드 기능에 들어가는 것들.
    MediaRecorder recorder;
    String filename;
    MediaPlayer player;
    int position = 0;
    // 다시 시작 기능을 위한 현재 재생 위치 확인 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button btn;
        TextView tv;

        tv=findViewById(R.id.textView2);

        btn = findViewById(R.id.bt_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } });
        Intent intent = getIntent();
        String name = intent.getExtras().get("name").toString();
        System.out.println("이름"+name);

        tv.setText(name);


        permissionCheck();
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        System.out.println(sdcard+"현재 경로는");

        filename = sdcard + File.separator + "recorded.mp4";

        Log.d("Main3Activity", "저장할 파일 명 : " + filename);

        findViewById(R.id.bt_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio();
            }
        });
        findViewById(R.id.bt_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAudio();
            }
        });
        findViewById(R.id.bt_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordAudio();
            }
        });
        findViewById(R.id.bt_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeAudio();
            }
        });

        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAudio();
            }
        });
        btn = findViewById(R.id.bt_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.bt_recordStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });
    }

    private void recordAudio() {
        recorder = new MediaRecorder();

        /* 그대로 저장하면 용량이 크다.
         * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
         * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
         * 따라서 용량이 크므로, 압축할 필요가 있음 */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 어디에서 음성 데이터를 받을 것인지
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 압축 형식 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "녹음 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "녹음 중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio() {
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(filename);
            player.prepare();
            player.start();

            Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseAudio() {
        if (player != null) {
            position = player.getCurrentPosition();
            player.pause();

            Toast.makeText(this, "일시정지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resumeAudio() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(position);
            player.start();

            Toast.makeText(this, "재시작됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();

            Toast.makeText(this, "중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
}