package com.banksecure.domain;

import java.math.BigDecimal;


public class Seguro {
    private Long id;
    private String titulo;
    private String descricao;
    private BigDecimal cobertura;
    private BigDecimal valorBase;

    public Seguro(Long id, String titulo, String descricao, BigDecimal cobertura, BigDecimal valorBase) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cobertura = cobertura;
        this.valorBase = valorBase;
    }

    @Override
    public String toString() {
        return "Seguro{" +
                "id=" + id +
                ", Título='" + titulo + '\'' +
                ", Descricao='" + descricao + '\'' +
                ", Cobertura=" + cobertura +
                ", Valor Prêmio base=" + valorBase +
                '}';
    }
     public Long getId() {
        return id;
     }
     public void setId(Long id) {
        this.id = id;
     }
        public String getTitulo() {
            return titulo;
        }
        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }
        public String getDescricao() {
            return descricao;
        }
        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
        public BigDecimal getCobertura() {
            return cobertura;
        }
        public void setCobertura(BigDecimal cobertura) {
            this.cobertura = cobertura;
        }
        public BigDecimal getValorBase() {
            return valorBase;
        }
        public void setValorBase(BigDecimal valorBase) {
            this.valorBase = valorBase;
        }
    
}
