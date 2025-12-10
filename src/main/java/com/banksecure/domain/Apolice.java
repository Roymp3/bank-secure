package com.banksecure.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Apolice {

    private Long id;
    private Long cliente_id;
    private Long seguro_id;
    private Long funcionario_id;
    private BigDecimal valorFinal;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean renovada;

    public Apolice(Long id, Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim, boolean renovada) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.renovada = renovada;
    }

    public Apolice(Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim, boolean renovada) {
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.renovada = renovada;
    }

    public Apolice() {
    }

    public boolean isRenovada() {
        return renovada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public Long getSeguro_id() {
        return seguro_id;
    }

    public Long getFuncionario_id() {
        return funcionario_id;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Cliente ID: ").append(cliente_id).append("\n");
        sb.append("Seguro ID: ").append(seguro_id).append("\n");
        sb.append("Funcionario ID: ").append(funcionario_id).append("\n");
        sb.append("Valor: ").append(valorFinal).append("\n");
        sb.append("Data inicio: ").append(dataInicio).append("\n");
        sb.append("Data fim: ").append(dataFim).append("\n");
        sb.append("Renovada: ").append(renovada).append("\n");
        sb.append("----------------------------\n");
        return sb.toString();
    }
}
