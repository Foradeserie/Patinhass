package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class listar_usuarios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuarioAdapter adapter;
    private List<Login> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);

        recyclerView = findViewById(R.id.lista_usuarios);
        usuarios = new ArrayList<>();
        adapter = new UsuarioAdapter(usuarios);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        listarUsuarios();
    }

    private void listarUsuarios() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        usuarios.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Login usuario = document.toObject(Login.class);
                            usuarios.add(usuario);
                        }
                        adapter.notifyDataSetChanged(); // Atualize o RecyclerView com os dados obtidos
                    } else {
                        Toast.makeText(this, "Erro ao listar usuários: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void limparTabela() {
        usuarios.clear();
        adapter.notifyDataSetChanged();
    }
}