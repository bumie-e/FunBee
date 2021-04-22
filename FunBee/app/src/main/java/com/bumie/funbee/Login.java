package com.bumie.funbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button alicebtn, bobbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        alicebtn = findViewById(R.id.alice);
        bobbtn = findViewById(R.id.bob);

        alicebtn.setOnClickListener(v -> {
            Call call = new Call();
            call.loginAsAlice();
            startActivity(new Intent(Login.this, FunTime.class));
        });
        bobbtn.setOnClickListener(v -> {
            Call call = new Call();
            call.loginAsBob();
            startActivity(new Intent(Login.this, FunTime.class));
        });
    }
}