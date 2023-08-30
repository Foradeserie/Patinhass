package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listar_usuarios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuarioDAO dao;
    private List<Login> usuarios;
    private List<Login> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        recyclerView = findViewById(R.id.lista_usuarios);
         dao = new UsuarioDAO(this);
         usuarios = dao.obterTodos();
        usuariosFiltrados.addAll(usuarios);

        UsuarioAdapter adapter = new UsuarioAdapter(usuariosFiltrados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public void limparTabela(View view) {
        dao.limparTabela();
        Toast.makeText(this, "Tabela limpa!", Toast.LENGTH_SHORT).show();
    }
}