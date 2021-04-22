package com.bumie.funbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button alicebtn, bobbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sh = getSharedPreferences("User", MODE_PRIVATE);
        Boolean value = sh.getBoolean("user", false);
        if (value == true | false){
            startActivity(new Intent(Login.this, FunTime.class));
        }
        setContentView(R.layout.activity_login);

        alicebtn = findViewById(R.id.alice);
        bobbtn = findViewById(R.id.bob);
        chooseUser();
    }
    public void chooseUser(){
        SharedPreferences sh = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        alicebtn.setOnClickListener(v -> {
            editor.putBoolean("user", true);
            editor.apply();
            startActivity(new Intent(Login.this, FunTime.class));
        });
        bobbtn.setOnClickListener(v -> {
            editor.putBoolean("user", false);
            editor.apply();
            startActivity(new Intent(Login.this, FunTime.class));
        });


    }
}