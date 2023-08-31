package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nomeEditText, senhaEditText;
    private Button loginButton;
    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEditText = findViewById(R.id.editTextText3);
        senhaEditText = findViewById(R.id.editTextTextPassword);

        dao = new UsuarioDAO(this);
        Button loginButton = findViewById(R.id.button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditText.getText().toString();
                String senha = senhaEditText.getText().toString();

                if (verificarLogin(nome, senha)) {
                    Intent intent = new Intent(MainActivity.this, Opcoes.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login falhou. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cadastrarButton = findViewById(R.id.button4);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        Button bancoButton = findViewById(R.id.button2);
        bancoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listar_usuarios.class);
                startActivity(intent);
            }
        });
    }


    private boolean verificarLogin(String email, String senha) {
        return dao.verificarLogin(email, senha);
    }
}
