package com.bumie.funbee;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VonageCalls {
    public static String BASE_URL= "https://api.nexmo.com";

    public void getUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<List<Model>> call = jsonapi.getUsers();
        call.enqueue(new Callback<java.util.List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                // TODO: Update the UI here
                // TODO: Populate the recyler view
                // TODO: On tap opens conversation
                // TODO: Tapping on profile picture opens profile information

            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }

        });
    }
    public void getUser(String user_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<java.util.List<Model>> call = jsonapi.getUser(user_id);
        call.enqueue(new Callback<java.util.List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<java.util.List<Model>> response) {
                // TODO: Update the Profile UI here
            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
    public void getMember(String conversation_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<java.util.List<Model>> call = jsonapi.getMembers(conversation_id);
        call.enqueue(new Callback<java.util.List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<java.util.List<Model>> response) {
                // TODO: Update the UI for showing members in a conversation
                // TODO: Populate the recyler view
                // TODO: On tap opens conversation. This may not be needed again
            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
    public void getConversations(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<java.util.List<Model>> call = jsonapi.getConversations();
        call.enqueue(new Callback<java.util.List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<java.util.List<Model>> response) {
                // TODO: Update the UI here
                // TODO: Populate the recyler view
                // TODO: On tap opens conversation
            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
    public void getConversation(String conversation_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<java.util.List<Model>> call = jsonapi.getAConversation(conversation_id);
        call.enqueue(new Callback<java.util.List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<java.util.List<Model>> response) {
                // TODO: Update the UI here
                // TODO: Return information about a conversation including all members present
                // TODO: Save conversation either on firebase or on sqlite or never.
            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
    public void createUsers(String jwt, String name, String display_name, String image_url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<Model> call = jsonapi.createUser(jwt, name, display_name, image_url);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                String user_id = response.body().getId();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    public void updateUsers(String jwt, String user_id, String name, String display_name, String image_url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<Model> call = jsonapi.updateUser(jwt, user_id, name, display_name, image_url);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                String user_id = response.body().getId();
                // TODO: Update the UI here
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }
    public void createConversation(String jwt, String name, String display_name, String image_url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<Model> call = jsonapi.createConversation(jwt, name, display_name, image_url);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                String conversation_id = response.body().getId();
                // TODO: Opens the chat fragment
                // TODO: Update the UI for the chat fragment
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }
    public void createMember(String jwt, String conversation_id, String user_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<Model> call = jsonapi.createMember(jwt, conversation_id, user_id);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                String member_id = response.body().getId();
                // TODO: Update the Conversation List UI here
                // TODO: Update the UI here
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }
}
