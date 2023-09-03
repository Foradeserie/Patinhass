package com.example.patinhas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {

    private EditText nome;
    private EditText dataNascimento;
    private EditText email;
    private EditText senha;
    private EditText estado;
    private EditText cidade;
    private Button cadastrarButton;
    private FirebaseFirestore db; // Firestore reference
    private FirebaseAuth mAuth;

    private Login login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        nome = findViewById(R.id.editTextText);
        dataNascimento = findViewById(R.id.editTextText2);
        email = findViewById(R.id.editTextText5);
        senha = findViewById(R.id.editTextText4);
        estado = findViewById(R.id.editTextText6);
        cidade = findViewById(R.id.editTextText7);
        cadastrarButton = findViewById(R.id.button);
        mAuth =FirebaseAuth.getInstance();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Defina o OnClickListener para o botão
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperardados();
                salvar();
            }
        });
    }

    private void salvar(){
            mAuth.createUserWithEmailAndPassword(login.getEmail(), login.getSenha())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(TelaCadastro.this,Opcoes.class));
                            }else{
                                Toast.makeText(TelaCadastro.this, "Erro ao criar conta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    public void recuperardados() {
        if (email.getText().toString().equals("") || senha.getText().toString().equals("")) {
            Toast.makeText(this, "Você deve preencher todos os dados", Toast.LENGTH_LONG).show(); // Você esqueceu o método .show() aqui
        }else{
            login = new Login();
            login.setNome(email.getText().toString());
            login.setSenha(senha.getText().toString());
        }
    }
}
