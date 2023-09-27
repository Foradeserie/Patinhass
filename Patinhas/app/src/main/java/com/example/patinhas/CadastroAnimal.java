package com.example.patinhas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CadastroAnimal implements Serializable {
    public String nome;
    public String porte;
    public String raca;
    public String genero;
    public String peso;
    public String personalidade;
    public String historia;

    private List<String> imageUrls;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    CadastroAnimal(String nome, String porte, String raca, String genero, String peso,
                   String personalidade, String historia){
    this.nome=nome;
    this.porte=porte;
    this.raca=raca;
    this.genero=genero;
    this.peso=peso;
    this.personalidade=personalidade;
    this.historia=historia;
    this.imageUrls = new ArrayList<>();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }
}
