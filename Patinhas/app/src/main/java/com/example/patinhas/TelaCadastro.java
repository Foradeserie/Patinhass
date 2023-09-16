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

public class TelaCadastro extends AppCompatActivity {

    private EditText nome;
    private EditText dataNascimento;
    private EditText email;
    private EditText senha;
    private EditText estado;
    private EditText cidade;
    private Button cadastrarButton;
    private FirebaseAuth autenticacao;
    private FirebaseFirestore db;

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
        cadastrarButton.setOnClickListener(view -> validarCadastroUsuario());

        autenticacao = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void salvarDadosUsuario(String userID) {
        String nomeUsuario = nome.getText().toString();
        String emailUsuario = email.getText().toString();
        String senhaUsuario = senha.getText().toString();
        String cidadeUsuario = cidade.getText().toString();
        String estadoUsuario = estado.getText().toString();
        String dataNascimentoUsuario= dataNascimento.getText().toString();

        //instanciando
        Login usuario = new Login(nomeUsuario, emailUsuario, senhaUsuario, cidadeUsuario, estadoUsuario, dataNascimentoUsuario);

        // Salve os dados do usuário no Firestore
        db.collection("Usuarios")
                .document(userID) //identifica a coleção e o documento
                .set(usuario)
                .addOnSuccessListener(documentReference -> {    //diz o que o aplicativo deve fazer dependendo do resultado
                    Intent intent = new Intent(TelaCadastro.this, Opcoes.class);
                    startActivity(intent);
                    Toast.makeText(TelaCadastro.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(TelaCadastro.this, "Erro ao salvar dados no Firestore", Toast.LENGTH_SHORT).show();
                });
    }

    private void validarCadastroUsuario() {
        String textoNome = nome.getText().toString();
        String textoEmail = email.getText().toString();
        String textoSenha = senha.getText().toString();
        String textoCidade = cidade.getText().toString();
        String textoEstado = estado.getText().toString();
        String textoDataNascimento= dataNascimento.getText().toString();
        if (!textoNome.isEmpty() && !textoEmail.isEmpty() && !textoSenha.isEmpty() && !textoCidade.isEmpty() && !textoEstado.isEmpty() && !textoDataNascimento.isEmpty()) {
            autenticacao.createUserWithEmailAndPassword(textoEmail, textoSenha) //cria o usu
                    .addOnCompleteListener(this, task -> {   //diz o que o aplicativo deve fazer dependendo do resultado
                        if (task.isSuccessful()) {
                            salvarDadosUsuario(autenticacao.getCurrentUser().getUid());
                        } else {
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
        } else {
            Toast.makeText(TelaCadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
