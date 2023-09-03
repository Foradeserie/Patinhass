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
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, senhaEditText;
    private Button loginButton, cadastrarButton, bancoButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // O usuário já está autenticado, redirecione-o para a tela de opções ou qualquer outra atividade desejada.
            Intent intent = new Intent(MainActivity.this, Opcoes.class);
            startActivity(intent);
            finish();
        }

        emailEditText = findViewById(R.id.editTextText3);
        senhaEditText = findViewById(R.id.editTextTextPassword);

        loginButton = findViewById(R.id.button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String senha = senhaEditText.getText().toString();

                fazerLogin(email, senha);
            }
        });

        cadastrarButton = findViewById(R.id.button4);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        bancoButton = findViewById(R.id.button2);
        bancoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listar_usuarios.class);
                startActivity(intent);
            }
        });
    }

    private void fazerLogin(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login bem-sucedido, redirecione para a tela de opções ou qualquer outra atividade desejada.
                            Intent intent = new Intent(MainActivity.this, Opcoes.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Login falhou, exiba uma mensagem de erro.
                            Toast.makeText(MainActivity.this, "Login falhou. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

