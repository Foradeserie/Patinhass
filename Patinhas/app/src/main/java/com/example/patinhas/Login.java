package com.example.patinhas;

import java.io.Serializable;

public class Login implements Serializable {
    private String nome;
    private String dataNascimento;;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private long id;

    public Login(){

    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataNascimento() {

        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setId(long id) {
        this.id = id;

    }
    @Override
    public String toString(){
        return nome;
    }

}

