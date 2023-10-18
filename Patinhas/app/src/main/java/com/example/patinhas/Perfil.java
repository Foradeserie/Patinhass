package com.example.patinhas;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Perfil extends AppCompatActivity {

    private ImageView perfilImageView;
    private TextView nomeTextView;
    private TextView sobre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil); // Certifique-se de que você tenha um layout correspondente


        nomeTextView = findViewById(R.id.textView3);
        sobre = findViewById(R.id.editTextText2);

        // Obtendo o ID do usuário atual
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Usuarios").document(userId);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Manipular erro
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // O documento existe, agora você pode obter os dados
                    String nome = documentSnapshot.getString("nome");
                    String dataNascimento= documentSnapshot.getString("dataNascimento");

                    // Atualizar a interface do usuário com os dados obtidos
                    nomeTextView.setText(nome);
                    sobre.setText(dataNascimento);
                }
            }
        });
    }
}
