package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.file.Files;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //뒤로가는 버튼 구현.
        Button btn_back_to_ac1;
        btn_back_to_ac1 = findViewById(R.id.btn_back_to_main);
        btn_back_to_ac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 여기에서 데이터베이스랑 연동해서
        // 현재 병원이 어떻게 예약되어 있는지를 확인해보자.



    }
}
