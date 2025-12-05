package com.banksecure.service;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;

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
    }

}
