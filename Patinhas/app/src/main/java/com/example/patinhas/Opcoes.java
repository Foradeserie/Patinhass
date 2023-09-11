package com.example.patinhas;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Opcoes extends AppCompatActivity {
    ImageView adotar;
    ImageView cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        adotar = findViewById(R.id.adotar);
        adotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opcoes.this, FeedAnimal.class);
                startActivity(intent);
            }
        });

        cadastrar = findViewById(R.id.cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opcoes.this, CadastroAnimal.class);
                startActivity(intent);
            }
        });
    }
}
