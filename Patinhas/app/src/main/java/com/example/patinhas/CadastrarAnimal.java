package com.example.patinhas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
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

    private Uri selectedImageUri;

    private List<Uri> selectedImageUris = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

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

        recyclerView = findViewById(R.id.recyclerViewImages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        imageAdapter = new ImageAdapter(selectedImageUris, this);
        recyclerView.setAdapter(imageAdapter);


        ImageView galeriaButton = findViewById(R.id.galeria);
        galeriaButton.setOnClickListener(view -> abrirGaleria(view));

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //qualquer código de manipulação padrão pode ser executado.
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SEU_CODIGO_DE_REQUISICAO) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getClipData() != null) { // Uma ou mais imagens foram selecionadas com sucesso.
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        selectedImageUri = data.getClipData().getItemAt(i).getUri();
                        selectedImageUris.add(selectedImageUri);
                    }
                } else if (data.getData() != null) {
                    selectedImageUri = data.getData();
                    selectedImageUris.add(selectedImageUri);
                }
                imageAdapter.notifyDataSetChanged(); //garante que os updades foram para a interface de usuario
                Toast.makeText(this, "Imagens selecionadas com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ocorreu um erro ao selecionar imagens.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void salvarDadosAnimal() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); //verifica se o usuario t autenticado ou não
        if (currentUser != null) {
            String userID = currentUser.getUid();
            String nomeAnimal = nome.getText().toString();
            String porteAnimal = porte.getText().toString();
            String racaAnimal = raca.getText().toString();
            String generoAnimal = genero.getText().toString();
            String pesoAnimal = peso.getText().toString();
            String personalidadeAnimal = personalidade.getText().toString();
            String historiaAnimal = historia.getText().toString();


            // Gera um UID único para o documento do animal
            String animalUID = UUID.randomUUID().toString();

            // Instanciando
            CadastroAnimal animal = new CadastroAnimal(nomeAnimal, porteAnimal, racaAnimal, generoAnimal,
                    pesoAnimal, personalidadeAnimal, historiaAnimal);

            // faz Upload das imagens para o Firebase Storage
            for (Uri imageUri : selectedImageUris) {
                String imageName = UUID.randomUUID().toString() + ".jpg"; // Gere um nome único para a imagem
                StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageName);

                imageRef.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Imagem carregada com sucesso
                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                // Adiciona a URL da imagem ao objeto animal
                                animal.addImageUrl(uri.toString());

                                // Salva os dados do animal no Firestore com o UID único
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
                                            Toast.makeText(CadastrarAnimal.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CadastrarAnimal.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
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
                        !textoHistoria.isEmpty() && selectedImageUri != null) {
                    salvarDadosAnimal();
                } else {
                    Toast.makeText(CadastrarAnimal.this, "Preencha todos os campos e selecione uma imagem", Toast.LENGTH_SHORT).show();
                }
            }

            public void abrirGaleria(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SEU_CODIGO_DE_REQUISICAO);
            }
        }