package com.example.patinhas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View; // Importe a classe View corretamente
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class CadastrarAnimal extends AppCompatActivity {

    public EditText nome;
    public EditText porte;
    public EditText raca;
    public EditText genero;
    public EditText peso;
    public EditText personalidade;
    public EditText historia;
    private Button buttonanimal;

    private FirebaseFirestore db;

    private static final int SEU_CODIGO_DE_REQUISICAO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal);

        nome = findViewById(R.id.nomeAnimal);
        porte = findViewById(R.id.porteAnimal);
        raca = findViewById(R.id.racaAnimal);
        genero = findViewById(R.id.generoAnimal);
        peso = findViewById(R.id.pesoAnimal);
        personalidade = findViewById(R.id.personalidade);
        historia = findViewById(R.id.historia);

        historia.setScroller(new Scroller(this));
        historia.setVerticalScrollBarEnabled(true);
        historia.setMovementMethod(new ScrollingMovementMethod());

        buttonanimal = findViewById(R.id.bCadastrarAnimal);
        buttonanimal.setOnClickListener(view -> validarCadastroAnimal());

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEU_CODIGO_DE_REQUISICAO) {
            if (resultCode == RESULT_OK && data != null) {
                // A imagem foi selecionada com sucesso.
                Uri selectedImageUri = data.getData();
                Toast.makeText(this, "Uri da imagem selecionada: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ocorreu um erro ao selecionar a imagem.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void salvarDadosAnimal() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userID = currentUser.getUid();
            String nomeAnimal = nome.getText().toString();
            String porteAnimal = porte.getText().toString();
            String racaAnimal = raca.getText().toString();
            String generoAnimal = genero.getText().toString();
            String pesoAnimal = peso.getText().toString();
            String personalidadeAnimal = personalidade.getText().toString();
            String historiaAnimal = historia.getText().toString();

            // Gerar um UID único para o documento do animal
            String animalUID = UUID.randomUUID().toString();

            // Instanciando o objeto CadastroAnimal
            CadastroAnimal animal = new CadastroAnimal(nomeAnimal, porteAnimal, racaAnimal, generoAnimal,
                    pesoAnimal, personalidadeAnimal, historiaAnimal);

            // Salvar os dados do animal no Firestore com o UID único
            db.collection("Cadastroanimal")
                    .document(animalUID)
                    .set(animal)
                    .addOnSuccessListener(documentReference -> {
                        Intent intent = new Intent(CadastrarAnimal.this, FeedAnimal.class);
                        startActivity(intent);
                        Toast.makeText(CadastrarAnimal.this, "Sucesso ao cadastrar animal", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CadastrarAnimal.this, "Erro ao salvar dados no Firestore", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void validarCadastroAnimal() {
        String textoNome = nome.getText().toString();
        String textoPorte = porte.getText().toString();
        String textoRaca = raca.getText().toString();
        String textoGenero = genero.getText().toString();
        String textoPeso = peso.getText().toString();
        String textoPersonalidade = personalidade.getText().toString();
        String textoHistoria = historia.getText().toString();

        if (!textoNome.isEmpty() && !textoPorte.isEmpty() && !textoRaca.isEmpty() &&
                !textoGenero.isEmpty() && !textoPeso.isEmpty() && !textoPersonalidade.isEmpty() &&
                !textoHistoria.isEmpty()) {
            salvarDadosAnimal();
        } else {
            Toast.makeText(CadastrarAnimal.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SEU_CODIGO_DE_REQUISICAO);
    }

}
