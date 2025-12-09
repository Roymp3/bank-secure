package com.banksecure.service;

import com.banksecure.domain.Seguro;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;

import java.math.BigDecimal;

public class SeguroService {

    public void validarSeguroDAO(Seguro seguro) {

        if(seguro.getTipo() == null ||
                seguro.getDescricao()==null ||
                seguro.getCobertura()==null ||
                seguro.getValorBase()==null){
            throw new EstruturaBancoException("Registros fornecidos não coincidem com a tabela Seguro");
        }

        if(seguro.getTipo().name().trim().isEmpty() ||
                seguro.getDescricao().trim().isEmpty() ||
                seguro.getCobertura().compareTo(BigDecimal.ZERO)<=0 ||
                seguro.getValorBase().compareTo(BigDecimal.ZERO)<=0){
            throw new DadosInvalidosException("Dados invalidos: campos não podem ser vazios ou negativos.");
        }
    }

    public void validarDeleteSeguro(Seguro seguro) {

        if (seguro == null) {
            throw new DadosInvalidosException("Seguro inválido para exclusão.");
        }

        if (seguro.getId() == null || seguro.getId() <= 0) {
            throw new DadosInvalidosException("ID inválido para exclusão.");
        }

        if (seguro.getTipo() == null || seguro.getTipo().name().trim().isEmpty()) {
            throw new DadosInvalidosException("Título inválido para exclusão.");
        }

        if (seguro.getValorBase() == null || seguro.getValorBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("Valor base inválido para exclusão.");
        }
    }

}
