package com.colini.models;

public class Aluno {
    private String nome;
    private String nascimento;
    private String cpf;
    private String email;
    private Notas notas;
    private String media;
    
    public Aluno(String nome, String nascimento, String cpf, String email, Notas notas, String media) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.email = email;
        this.notas = notas;
        this.media = media;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNascimento() {
        return nascimento;
    }
    
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Notas getNotas() {
        return notas;
    }
    
    public void setNotas(Notas notas) {
        this.notas = notas;
    }   

    
    public String getNota1() {
        return notas != null ? notas.getNota1() : ""; // sinceramente eu nao tenho ideia de como essas linhas abaixo funcionam mas se ta funcionando nao mexe
    }
    
    public String getNota2() {
        return notas != null ? notas.getNota2() : "";
    }
    
    public String getNota3() {
        return notas != null ? notas.getNota3() : "";
    }
    
    public String getNota4() {
        return notas != null ? notas.getNota4() : "";
    }
    
    public String getMedia() {
        return media;
    }
    
    public void setMedia(String media) {
        this.media = media;
    }
}
