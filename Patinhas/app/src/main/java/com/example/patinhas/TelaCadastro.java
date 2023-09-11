package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class TelaCadastro extends AppCompatActivity {

    private EditText nome;
    private EditText dataNascimento;
    private EditText email;
    private EditText senha;
    private EditText estado;
    private EditText cidade;
    private Button cadastrarButton;
    private FirebaseFirestore db;
    private FirebaseAuth autenticacao;

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
        autenticacao = FirebaseAuth.getInstance();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }
    public void salvar(View view) {
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                email.getText().toString(), senha.getText().toString()
        ).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Após a criação bem-sucedida do usuário, você pode salvar os dados no Firestore
                FirebaseFirestore db = ConfiguracaoFirebase.getFirebaseFirestore();
               
                db.collection("usuarios")
                        .document(login.getEmail())
                        .set(login)
                        .addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(TelaCadastro.this, Opcoes.class);
                            startActivity(intent);
                            Toast.makeText(TelaCadastro.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(TelaCadastro.this, "Erro ao salvar dados no Firestore", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Tratamento de erros de autenticação
                String excecao = "";
                try {
                    throw task.getException();
                } catch (FirebaseAuthWeakPasswordException e) {
                    excecao = "Digite uma senha mais forte!";
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    excecao = "Por favor, digite um e-mail válido";
                } catch (FirebaseAuthUserCollisionException e) {
                    excecao = "Esta conta já foi cadastrada.";
                } catch (Exception e) {
                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(TelaCadastro.this, excecao, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void validarCadastroUsuario(View view){

            String textoNome  = nome.getText().toString();
            String textoEmail = email.getText().toString();
            String textoSenha = Objects.requireNonNull(senha.getText()).toString();

            if ( !textoNome.isEmpty()){
                if ( !textoEmail.isEmpty()){
                    if ( !textoSenha.isEmpty()){

                        Login usuario = new Login();
                        usuario.setNome ( textoNome );
                        usuario.setEmail( textoEmail );
                        usuario.setSenha( textoSenha );

                        salvar( view );

                    }else {
                        Toast.makeText(TelaCadastro.this, "Preencha a senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(TelaCadastro.this, "Preencha o e-mail!",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(TelaCadastro.this, "Preencha o nome!",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
