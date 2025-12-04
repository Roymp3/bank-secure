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

    public Apolice(Long id, Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Apolice(Long cliente_id, Long seguro_id, Long funcionario_id, BigDecimal valorFinal, LocalDate dataInicio, LocalDate dataFim) {
        this.cliente_id = cliente_id;
        this.seguro_id = seguro_id;
        this.funcionario_id = funcionario_id;
        this.valorFinal = valorFinal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Apolice() {
    }

    public Long getId() {
        return id;
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
        return "Apolice [" +
                "id=" + id +
                ", cliente_id=" + cliente_id +
                ", seguro_id=" + seguro_id +
                ", funcionario_id=" + funcionario_id +
                ", valorFinal=" + valorFinal +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ']' + '\n';
    }
}
