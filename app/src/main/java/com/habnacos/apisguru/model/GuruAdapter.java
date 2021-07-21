package com.habnacos.apisguru.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.habnacos.apisguru.R;
import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.GuruViewHolder> {
    ArrayList<ApiGuru> apiGurus;
    RecyclerView recyclerView;
    Context context;
    private static OkHttpClient httpClient;

    public GuruAdapter(Context context, ArrayList<ApiGuru> apiGurus, RecyclerView recyclerView) {
        this.context = context;
        this.apiGurus = apiGurus;
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(this);
    }

    @NonNull
    @Override
    public GuruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_guru, null, false);
        return new GuruViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruViewHolder holder, int position) {
        holder.email.setText(apiGurus.get(position).getEmail());
        holder.name.setText(apiGurus.get(position).getName());
        holder.url.setText(apiGurus.get(position).getUrl());
        holder.title.setText(apiGurus.get(position).getTitle());
        holder.description.setText(apiGurus.get(position).getDescription());
        holder.version.setText(apiGurus.get(position).getVersion());

        LoadImage loadImage = new LoadImage(holder.logo);
        try {
            loadImage.execute(apiGurus.get(position).getLogo()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() { return this.apiGurus.size(); }

    public class GuruViewHolder extends RecyclerView.ViewHolder {
        TextView email, name, url, title, description, version;
        ImageView logo;

        public GuruViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.temEmail);
            name = itemView.findViewById(R.id.temName);
            url = itemView.findViewById(R.id.temUrl);
            title = itemView.findViewById(R.id.temTitle);
            description = itemView.findViewById(R.id.temDescription);
            version = itemView.findViewById(R.id.temVersion);
            logo = itemView.findViewById(R.id.temLogo);
        }
    }
}
