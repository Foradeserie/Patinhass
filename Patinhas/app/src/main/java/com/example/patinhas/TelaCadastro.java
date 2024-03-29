package com.example.patinhas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import  androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TelaCadastro extends AppCompatActivity {

    private EditText nome;
    private EditText dataNascimento;
    private EditText email;
    private EditText senha;
    private EditText estado;
    private EditText cidade;
    private Button cadastrarButton;
    private UsuarioDAO dao;

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
        dao = new UsuarioDAO(this);

        // Uncomment the following line to call the method that inserts fake data for testing
        insertFakeDataForTesting();
    }


    public void salvar(View view) {
        Login usuario = new Login();
        usuario.setNome(nome.getText().toString());
        usuario.setDataNascimento(dataNascimento.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        usuario.setEstado(estado.getText().toString());
        usuario.setCidade(cidade.getText().toString());

           long result= dao.inserir(usuario);

//        dao.inserir(usuario);

        long uniqueId = System.currentTimeMillis();

        if (result != -1) {
            usuario.setId(result);

            Toast.makeText(this, "Usuario inserido com id:" + usuario.getId(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Este email já está cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to insert fake data for testing purposes

    private void insertFakeDataForTesting() {
        Login fakeUser1 = new Login();
        fakeUser1.setNome("John Doe");
        fakeUser1.setDataNascimento("13/02/2002");
        fakeUser1.setEmail("john@example.com");
        fakeUser1.setSenha("123456");
        fakeUser1.setEstado("California");
        fakeUser1.setCidade("Los Angeles");
        dao.inserir(fakeUser1);

        Login fakeUser2 = new Login();
        fakeUser2.setNome("Jane Smith");
        fakeUser2.setDataNascimento("14/08/2002");
        fakeUser2.setEmail("jane@example.com");
        fakeUser2.setSenha("abcdef");
        fakeUser2.setEstado("New York");
        fakeUser2.setCidade("New York City");
        dao.inserir(fakeUser2);

        // Add more fake user instances as needed
    }
}
