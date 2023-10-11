package com.example.patinhas;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
public class FeedItem extends RecyclerView.ViewHolder{
        private int fotoPerfil;
        private String nomePessoa;
        private int fotos;
        private String legenda;
        private String botaoChamar;

        public FeedItem(View itemView) {
            super(itemView);
        }

        public FeedItem(int fotoPerfil, String nomePessoa, int fotos, String legenda, String botaoChamar, View itemView) {
            super(itemView);
            this.fotoPerfil = fotoPerfil;
            this.nomePessoa = nomePessoa;
            this.fotos = fotos;
            this.legenda = legenda;
            this.botaoChamar = botaoChamar;
        }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public String getLegenda() {
        return legenda;
    }

    public String getBotaoChamar() {
        return botaoChamar;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public int getFotos() {
        return fotos;
    }

    public void setBotaoChamar(java.lang.String botaoChamar) {
        this.botaoChamar = botaoChamar;
    }


}
