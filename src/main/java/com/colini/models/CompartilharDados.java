package com.colini.models;


public class CompartilharDados {
    private static String cpfAluno;
    private static String nomeAluno;
    private static String nascimentoAluno;
    private static String emailAluno;
    private static String nota1Aluno;
    private static String nota2Aluno;
    private static String nota3Aluno;
    private static String nota4Aluno;

    
    public static String getNome() {
        return nomeAluno;
    }

    public static void setNome(String nome) {
        nomeAluno = nome;
    }

    public static String getNascimento() {
        return nascimentoAluno;
    }

    public static void setNascimento(String nascimento) {
        nascimentoAluno = nascimento;
    }

    public static String getEmail() {
        return emailAluno;
    }
    
    public static void setEmail(String email) {
        emailAluno = email;
    }
    
    
    public static String getCpfAluno() {
        return cpfAluno;
    }
    
    public static void setCpfAluno(String cpf) {
        cpfAluno = cpf;
    }
    
    public static String getNota1() {
        return nota1Aluno;
    }
    
    public static void setNota1(String nota1) {
        nota1Aluno = nota1;
    }
    
    public static String getNota2() {
        return nota2Aluno;
    }
    
    public static void setNota2(String nota2) {
        nota2Aluno = nota2;
    }
    
    public static String getNota3() {
        return nota3Aluno;
    }
    
    public static void setNota3(String nota3) {
        nota3Aluno = nota3;
    }
    
    public static String getNota4() {
        return nota4Aluno;
    }
    
    public static void setNota4(String nota4) {
        nota4Aluno = nota4;
    }
}
