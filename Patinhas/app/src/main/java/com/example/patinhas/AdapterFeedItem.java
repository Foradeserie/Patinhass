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
        FeedViewHolder fvHelper =(FeedViewHolder) holder;
        FeedItem item = feedItems.get(position);
        fvHelper.imageView.setImageResource(item.getFotoPerfil());
        fvHelper.textView1.setText(item.getNomePessoa());
        fvHelper.imageView.setImageResource(item.getFotos());
        fvHelper.fotos.setText(item.getLegenda());

        // Configurar o clique do botão
        fvHelper.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para o clique do botão aqui
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView fotos;
        Button button;

            FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.perfil);
            textView1 = itemView.findViewById(R.id.nome);
            imageView = itemView.findViewById(R.id.fotos);
            fotos = itemView.findViewById(R.id.legenda);
            button = itemView.findViewById(R.id.chamar);
        }
    }
}
