package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         btn = (Button)findViewById(R.id.button3);
         System.out.println("asdf");
         btn.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(MainActivity.this,Main2Activity.class);
                 startActivity(i);
             }
         });

    }
}
