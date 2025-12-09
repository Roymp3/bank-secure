package com.banksecure.domain;

import com.banksecure.enums.TipoDeSeguroEnum;
import java.math.BigDecimal;


public class Seguro {
    private Long id;
    private TipoDeSeguroEnum tipo = TipoDeSeguroEnum.SEGURO_AUTO;
    private String descricao;
    private BigDecimal cobertura;
    private BigDecimal valorBase;

    public Seguro(Long id, TipoDeSeguroEnum tipo, String descricao, BigDecimal cobertura, BigDecimal valorBase) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.cobertura = cobertura;
        this.valorBase = valorBase;
    }

    public Seguro(TipoDeSeguroEnum tipo, String descricao, BigDecimal cobertura, BigDecimal valorBase) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.cobertura = cobertura;
        this.valorBase = valorBase;
    }

    public Seguro() {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.cobertura = cobertura;
        this.valorBase = valorBase;
    }


    @Override
    public String toString() {
        return "Seguro{" +
                "id=" + id +
                ", Título='" + tipo + '\'' +
                ", Descricao='" + descricao + '\'' +
                ", Cobertura=" + cobertura +
                ", Valor Prêmio base=" + valorBase +
                '}';
    }

    public String mostrarDadosDoSeguro() {
        StringBuilder dados = new StringBuilder();
        dados.append("================== Dados do Seguro ===================");
        dados.append("Id: " + id + "\n");
        dados.append("Tipo: " + tipo + "\n");
        dados.append("Descrição: " + descricao + "\n");
        dados.append("Cobertura: " + cobertura + "\n");
        dados.append("Valor Prêmio base: " + valorBase + "\n");

        return dados.toString();
    }

     public Long getId() {
        return id;
     }
     public void setId(Long id) {
        this.id = id;
     }
     public TipoDeSeguroEnum getTipo() {return tipo;}
     public void setTipo(TipoDeSeguroEnum tipo) {
            this.tipo = tipo;
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
