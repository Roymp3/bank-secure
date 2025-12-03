package com.banksecure.exception;

public class BancoVazioException extends RuntimeException {
    public BancoVazioException() {
        super("O banco não possui registros. ");
    }

}
