package com.bumie.funbee.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumie.funbee.JSONAPI;
import com.bumie.funbee.Model;
import com.bumie.funbee.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumie.funbee.VonageCalls.BASE_URL;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    public void getConversation(String conversation_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<List<Model>> call = jsonapi.getAConversation(conversation_id);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<List<Model>> response) {
                // TODO: Update the UI here
                // TODO: Return information about a conversation including all members present
                // TODO: Save conversation either on firebase or on sqlite or never.

            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
}