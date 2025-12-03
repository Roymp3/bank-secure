package com.banksecure.domain;

public class Funcionario {

    private Long id;
    private String usuario;
    private String senha;

    public Funcionario(Long id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String toString(){
        return "Funcionario{id=" + id + ", usuario='" + usuario + "', senha='" + senha + "'}";
    }
    
}
