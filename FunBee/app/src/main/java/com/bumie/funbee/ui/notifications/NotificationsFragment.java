package com.bumie.funbee.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumie.funbee.CustomAdapter;
import com.bumie.funbee.JSONAPI;
import com.bumie.funbee.Model;
import com.bumie.funbee.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumie.funbee.VonageCalls.BASE_URL;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Model> arrayList;
    CustomAdapter adapter;
    ImageView imageView;
    TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        name = root.findViewById(R.id.display_name);
        imageView = root.findViewById(R.id.profile_pic);
        rv = root.findViewById(R.id.rv_view_users);

        linearLayoutManager = new LinearLayoutManager(getContext());
        // setting layout manager for each recyler view
        rv.setLayoutManager(linearLayoutManager);
        // creating an empty array list
        arrayList = new ArrayList<Model>();
        // call retrofit before setting adapter
        getUsers();
        // adapter class with context
        adapter = new CustomAdapter(getContext(), arrayList);
        rv.setAdapter(adapter);

        return root;
    }

    public void getUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONAPI jsonapi = retrofit.create(JSONAPI.class);
        Call<List<Model>> call = jsonapi.getUsers();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<java.util.List<Model>> call, Response<List<Model>> response) {
                // TODO: Update the UI here
                // TODO: Populate the recyler view
                // TODO: On tap opens conversation
                // TODO: Tapping on profile picture opens profile information
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code : " + response.code(), Toast.LENGTH_LONG).show();
                }
                for (int i = 0; i < response.body().size(); i++) {
                    arrayList.add(new Model(response.body().get(i).getName()));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<java.util.List<Model>> call, Throwable t) {

            }
        });
    }
}