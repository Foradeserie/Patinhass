package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; // Importe a classe Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nomeEditText, senhaEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEditText = findViewById(R.id.editTextText3);
        senhaEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button3);

        // appDataBase = new AppDataBase(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeEditText.getText().toString();
                String senha = senhaEditText.getText().toString();

                if (verificarLogin(nome, senha)) {
                    // Login bem-sucedido, direcione para a próxima tela
                    // Implemente essa parte de acordo com o seu fluxo de navegação
                    // Por exemplo: startActivity(new Intent(MainActivity.this, TelaPrincipal.class));
                } else {
                    // Login falhou, exiba mensagem de erro
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

    private boolean verificarLogin(String nome, String senha) {
        // Lógica de verificação de login
        // Implemente de acordo com o seu sistema de autenticação
        return false;
    }
}
