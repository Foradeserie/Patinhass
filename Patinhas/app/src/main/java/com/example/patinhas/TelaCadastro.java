package com.example.patinhas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TelaCadastro extends AppCompatActivity {

    private EditText nome;
    private DatePicker dataNascimento;
    private EditText email;
    private EditText senha;
    private Spinner spinnerEstado;
    private Spinner cidade;
    private Button cadastrarButton;
    private FirebaseAuth autenticacao;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        nome = findViewById(R.id.editTextText);
        dataNascimento = findViewById(R.id.datePicker);
        email = findViewById(R.id.editTextText5);
        senha = findViewById(R.id.editTextText4);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
        cidade = findViewById(R.id.spinnerCidade); // Certifique-se de ter um Spinner com o ID correto no seu layout

        // Configurar um listener para o Spinner de estados
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atualizarCidades(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada a fazer aqui
            }
        });


    }

    private void atualizarCidades(int estadoSelecionado){
        // Obter o array de cidades com base na seleção de estado
        String[] cidadesArray;

        switch (estadoSelecionado) {
            case 1: // Acre
                cidadesArray = getResources().getStringArray(R.array.cidades_acre);
                break;
            case 2: // Alagoas
                cidadesArray = getResources().getStringArray(R.array.cidades_Alagoas);
                break;
            case 3:
                cidadesArray=getResources().getStringArray(R.array.cidades_goias);
                break;
            case 4:
                cidadesArray=getResources().getStringArray(R.array.cidades_Maranhão);
                break;
            case 5:
                cidadesArray=getResources().getStringArray(R.array.cidadesMatoGrosso);
                break;
            case 6:
                cidadesArray=getResources().getStringArray(R.array.cidades_MatoGrossodoSul);
                break;
            case 7:
                cidadesArray=getResources().getStringArray(R.array.cidades_MinasGerais);
                break;
            case 8:
                cidadesArray=getResources().getStringArray(R.array.cidades_Para);
                break;
            case 9:
                cidadesArray=getResources().getStringArray(R.array.cidades_Paraiba);
                break;
            case 10:
                cidadesArray=getResources().getStringArray(R.array.cidades_Parana);
                break;
            case 11:
                cidadesArray=getResources().getStringArray(R.array.cidades_Pernambuco);
                break;
            case 12:
                cidadesArray=getResources().getStringArray(R.array.cidades_Piaui);
                break;
            case 13:
                cidadesArray=getResources().getStringArray(R.array.cidades_RiodeJaneiro);
                break;
            case 14:
                cidadesArray=getResources().getStringArray(R.array.cidades_RioGrandedoNorte);
                break;
            case 15:
                cidadesArray=getResources().getStringArray(R.array.cidades_RioGrandeDoSul);
                break;
            case 16:
                cidadesArray=getResources().getStringArray(R.array.cidades_Rondonia);
                break;
            case 17:
                cidadesArray=getResources().getStringArray(R.array.cidades_Roraima);
                break;
            case 18:
                cidadesArray=getResources().getStringArray(R.array.cidades_SantaCatarina);
                break;
            case 19:
                cidadesArray=getResources().getStringArray(R.array.cidades_SaoPaulo);
                break;
            case 20:
                cidadesArray=getResources().getStringArray(R.array.cidades_Sergipe);
                break;
            case 21:
                cidadesArray=getResources().getStringArray(R.array.cidades_Tocantins);
                break;
            default:
                cidadesArray = new String[]{"Selecione a cidade"};
                break;
        }




        // Configurar o adaptador para o Spinner de cidades
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cidadesArray);
        adapterCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cidade.setAdapter(adapterCidades);




        cadastrarButton = findViewById(R.id.button);
        cadastrarButton.setOnClickListener(view -> validarCadastroUsuario());

        autenticacao = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private boolean isEstadoCidadeSelecionados() {
        String estadoSelecionado = spinnerEstado.getSelectedItem().toString();
        String cidadeSelecionada = cidade.getSelectedItem().toString();

        return !estadoSelecionado.equals("Selecione o estado") && !cidadeSelecionada.equals("Selecione a cidade");

    }

    private void salvarDadosUsuario(String userID) {
        String nomeUsuario = nome.getText().toString();
        String emailUsuario = email.getText().toString();
        String senhaUsuario = senha.getText().toString();
        String cidadeUsuario = cidade.getSelectedItem().toString();
        String[] estadoUsuario = getResources().getStringArray(R.array.estados);
        String estadoSelecionado = estadoUsuario[spinnerEstado.getSelectedItemPosition()];
        // Obtendo a data de nascimento do DatePicker
        int dia = dataNascimento.getDayOfMonth();
        int mes = dataNascimento.getMonth();
        int ano = dataNascimento.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);
        Date dataNascimentoUsuario = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataNascimentoFormatada = sdf.format(dataNascimentoUsuario);



        // Instanciando o usuário
        Login usuario = new Login(nomeUsuario, emailUsuario, senhaUsuario, cidadeUsuario, estadoSelecionado, dataNascimentoFormatada);

        // Salvar os dados do usuário no Firestore
        db.collection("Usuarios")
                .document(userID)
                .set(usuario)
                .addOnSuccessListener(documentReference -> {
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
        String estadoSelecionado = spinnerEstado.getSelectedItem().toString();
        String cidadeSelecionada = cidade.getSelectedItem().toString();

        // Verifica se todos os campos estão preenchidos e se o estado e a cidade foram selecionados
        if (!textoNome.isEmpty() && !textoEmail.isEmpty() && !textoSenha.isEmpty() &&
                !estadoSelecionado.equals("Selecione o estado") && !cidadeSelecionada.equals("Selecione a cidade")) {
            autenticacao.createUserWithEmailAndPassword(textoEmail, textoSenha)
                    .addOnCompleteListener(this, task -> {
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
            Toast.makeText(TelaCadastro.this, "Preencha todos os campos e selecione o estado e a cidade", Toast.LENGTH_SHORT).show();
        }
    }
}