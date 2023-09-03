package com.example.patinhas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Login> usuarios;

    public UsuarioAdapter(List<Login> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Login usuario = usuarios.get(position);
        holder.bind(usuario);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeTextView, datanascTextView, emailTextView, senhaTextView, estadoTextView, cidadeTextView;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            datanascTextView = itemView.findViewById(R.id.datanascTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            senhaTextView = itemView.findViewById(R.id.senhaTextView);
            estadoTextView = itemView.findViewById(R.id.estadoTextView);
            cidadeTextView = itemView.findViewById(R.id.cidadeTextView);
        }

        public void bind(Login usuario) {
            nomeTextView.setText(usuario.getNome());
            datanascTextView.setText(usuario.getDataNascimento());
            emailTextView.setText(usuario.getEmail());
            senhaTextView.setText(usuario.getSenha());
            estadoTextView.setText(usuario.getEstado());
            cidadeTextView.setText(usuario.getCidade());
        }
    }
}
