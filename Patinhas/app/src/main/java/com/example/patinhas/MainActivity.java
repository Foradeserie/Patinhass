package com.example.patinhas;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText campoSenha;
    private EditText campoEmail;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        campoSenha = findViewById(R.id.editTextText3);
        campoEmail = findViewById(R.id.editTextTextPassword);
    }

    public void logarUsuario( Login usuario ){

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {

            if ( task.isSuccessful() ){
                abrirTelaPrincipal();

            }else {

                String excecao = "";
                try {
                    throw task.getException();
                }catch ( FirebaseAuthInvalidUserException e){
                    excecao = "Usuário não está cadastrado.";
                }catch ( FirebaseAuthInvalidCredentialsException e){
                    excecao = "E-mail e senha não correspondem a um usuário cadastrado.";
                }catch ( Exception e ){
                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Erro ao fazer login!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validarAutenticacaoUsuario(View view){

        String textoEmail = campoEmail.getText().toString();
        String textoSenha = Objects.requireNonNull(campoSenha.getText()).toString();

        if ( !textoEmail.isEmpty()){
            if ( !textoSenha.isEmpty()){

                Login usuario = new Login();
                usuario.setEmail( textoEmail );
                usuario.setSenha( textoSenha );

                logarUsuario( usuario );

            }else {
                Toast.makeText(MainActivity.this, "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MainActivity.this, "Preencha o e-mail!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if ( usuarioAtual != null ){
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaCadastro(View view){
        Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
        startActivity( intent );
    }
    public void abrirTelaPrincipal(){
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity( intent );
    }
}
