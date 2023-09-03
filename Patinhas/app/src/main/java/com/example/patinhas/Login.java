package com.example.patinhas;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.Map;

public class Login implements Serializable {
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private long id;

    public Login() {
        // Construtor vazio necessário para Firestore
    }

    @PropertyName("nome")
    public String getNome() {
        return nome;
    }

    @PropertyName("nome")
    public void setNome(String nome) {
        this.nome = nome;
    }

    @PropertyName("dataNascimento")
    public String getDataNascimento() {
        return dataNascimento;
    }

    @PropertyName("dataNascimento")
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("senha")
    public String getSenha() {
        return senha;
    }

    @PropertyName("senha")
    public void setSenha(String senha) {
        this.senha = senha;
    }

    @PropertyName("estado")
    public String getEstado() {
        return estado;
    }

    @PropertyName("estado")
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PropertyName("cidade")
    public String getCidade() {
        return cidade;
    }

    @PropertyName("cidade")
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @PropertyName("id")
    public long getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome;
    }
}
