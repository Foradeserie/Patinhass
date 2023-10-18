package com.example.patinhas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patinhas.FeedItem;
import com.example.patinhas.R;

import java.util.List;

public class AdapterFeedItem extends RecyclerView.Adapter<AdapterFeedItem.FeedViewHolder>{

    List<FeedItem> feedItems;
    private Context context;

    public AdapterFeedItem(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_feedadapter, parent, false);
        FeedViewHolder fvHelper= new FeedViewHolder(view);
        return fvHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedViewHolder fvHelper = (FeedViewHolder) holder;
        FeedItem item = feedItems.get(position);

        fvHelper.imageView.setImageDrawable(null);


//        fvHelper.imageView.setImageResource(item.getFotoPerfil());
//        fvHelper.textView1.setText(item.getNomePessoa());
//        fvHelper..setImageResource(item.getImageUrl());
        fvHelper.legenda.setText(item.getHistoria());

        fvHelper.textView1.setText(item.getNome());

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView legenda;
        Button button;

            FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.perfil);
            textView1 = itemView.findViewById(R.id.nome);
            imageView = itemView.findViewById(R.id.fotos);
            legenda = itemView.findViewById(R.id.legenda);
            button = itemView.findViewById(R.id.chamar);
        }
    }
}
