package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn,btn2;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 진료예약을 누를 시 액티비티 전환 //mainactivity 2로 전환..

         btn = (Button)findViewById(R.id.btn_reservation);
         btn.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(MainActivity.this,Main2Activity.class);
                 startActivity(i);
             }
         });

        btn2 = (Button)findViewById(R.id.btn_lookup);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main4Activity.class);
                startActivity(i);
            }
        });



    }



}
