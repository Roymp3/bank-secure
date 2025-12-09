package com.banksecure.domain;

import java.time.LocalDate;

public class Cliente {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }

    public String mostrarDadosDoCliente(){
        StringBuilder dados = new StringBuilder();
        dados.append("================== Dados do cliente ===================");
        dados.append("id: " + id + "\n");
        dados.append("Nome: " + nome + "\n");
        dados.append("CPF: " + cpf + "\n");
        dados.append("Data de Nascimento: " + dataNascimento + "\n");

        return dados.toString();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public LocalDate getDataNascimento() {return dataNascimento;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}
}

