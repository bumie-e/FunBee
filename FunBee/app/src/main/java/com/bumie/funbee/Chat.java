package com.bumie.funbee;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chat extends AppCompatActivity {
    private String baseURL = "https://tidy-falcon-37.loca.lt/";
    ImageButton send;
    EditText edtxt;
    RecyclerView rv;
    ArrayList<Model> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        send = findViewById(R.id.button_chatbox_send);
        edtxt = findViewById(R.id.edittext_chatbox);
        rv = findViewById(R.id.rv);

        send.setOnClickListener(v ->
                sendMessage(edtxt.getText().toString()));
        edtxt.setText("");
    }

    private void sendMessage(String message){
        messagesList.add(new Model(message, CustomAdapterB.MESSAGE_TYPE_OUT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI placeholderApi = retrofit.create(JSONAPI.class);
        Call<Model> call = placeholderApi.getPrediction(message);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(Chat.this, "Post successfully sent! Code : " + response.code(), Toast.LENGTH_LONG).show();
                    messagesList.add(new Model(response.body().getMessage(), CustomAdapterB.MESSAGE_TYPE_IN));

                    CustomAdapterB adapter = new CustomAdapterB(Chat.this, messagesList);
                    rv.setLayoutManager(new LinearLayoutManager(Chat.this));
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.i("Chat","Unable to submit"+t.getMessage());
            }
        });
    }


}