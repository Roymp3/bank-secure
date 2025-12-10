package com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApoliceService {

    public void validarApoliceDAO(Apolice apolice) {

        if (apolice == null) {
            throw new IllegalArgumentException("Apolice vazia!");
        }

        if (apolice.getCliente_id() == null ||
                apolice.getSeguro_id() == null ||
                apolice.getFuncionario_id() == null ||
                apolice.getValorFinal() == null ||
                apolice.getDataInicio() == null ||
                apolice.getDataFim() == null) {

            throw new DadosInvalidosException("Dados da apólice incompleto");
        }

        if (apolice.getValorFinal().compareTo(BigDecimal.ZERO) == -1){
            throw new DadosInvalidosException("Valor final da apólice inválido");
        }

        if (apolice.getDataInicio().isBefore(LocalDate.now()) || apolice.getDataFim().isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("Data da apólice inválida");
        }
    }
}
