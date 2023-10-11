package com.example.patinhas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//TALVEZ EU APAGUE
public class Feed extends AppCompatActivity {
    List<FeedItem> feedItems;
    RecyclerView recyclerview;
    private AdapterFeedItem adapterFeedItem = null;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerview = findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        feedItems = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Lista de nomes das coleções que você quer obter dados
        List<String> colecoes = Arrays.asList("CadastroAnimal", "Usuarios");

        // Contador para controlar o número de consultas completadas
        AtomicInteger consultasCompletadas = new AtomicInteger(0);

        for (String colecao : colecoes) {
            db.collection(colecao)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Aqui você cria uma instância de FeedItem com os dados do documento
                                    FeedItem feedItem = document.toObject(FeedItem.class);
                                    feedItems.add(feedItem);
                                }
                            } else {
                                Log.w("Error getting documents.", task.getException());
                            }

                            // Incrementa o contador de consultas completadas
                            int consultasCompletas = consultasCompletadas.incrementAndGet();

                            // Se todas as consultas foram concluídas, configure o adaptador e a RecyclerView
                            if (consultasCompletas == colecoes.size()) {
                                adapterFeedItem = new AdapterFeedItem(feedItems);
                                recyclerview.setAdapter(adapterFeedItem);
                                adapterFeedItem.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}
