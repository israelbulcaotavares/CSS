package com.tavares.teste.firebase.entitites;

/**
 * Created by jamiltondamasceno.
 */

public class Postagem {

    private String nome;
    private String postagem;
    private int imagem;
    private String tempoDePostagem;

    public Postagem() {
    }

    public Postagem(String nome, String postagem, int imagem, String tempoDePostagem) {
        this.nome = nome;
        this.postagem = postagem;
        this.imagem = imagem;
        this.tempoDePostagem = tempoDePostagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPostagem() {
        return postagem;
    }

    public void setPostagem(String postagem) {
        this.postagem = postagem;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getTempoDePostagem() {
        return tempoDePostagem;
    }

    public void setTempoDePostagem(String tempoDePostagem) {
        this.tempoDePostagem = tempoDePostagem;
    }
}
