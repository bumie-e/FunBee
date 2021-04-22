package com.bumie.funbee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    List<Model> model;
    Context context;

    public CustomAdapter(Context context, List<Model> model){
        this.context = context;
        this.model = model;
    }
    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomAdapter.CustomViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
       /* Picasso.with(context)
                .load(model.get(position).getImage_url())
                .placeholder(R.drawable.ic_baseline_chat_24)
                .error(R.drawable.ic_baseline_chat_24)
                .into(holder.profile_pic);*/
        Model post = model.get(position);
        holder.name.setText(post.getName());
        holder.callbtn.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), Chat.class);
            i.putExtra("User_ID", post.getId());
            i.putExtra("User_name", post.getName());
            context.startActivity(i);
        });
        // TODO: use card view instead of profile pic to display user
        holder.profile_pic.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), Profile.class);
            i.putExtra("User_ID", post.getId());
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_pic, callbtn;
        TextView name;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.display_name);
            profile_pic = itemView.findViewById(R.id.profile_pic);
            callbtn = itemView.findViewById(R.id.startCallButton);
        }
    }
}
