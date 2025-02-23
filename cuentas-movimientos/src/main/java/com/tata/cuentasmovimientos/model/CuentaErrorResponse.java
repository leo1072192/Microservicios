package com.tata.cuentasmovimientos.model;

public class CuentaErrorResponse {
    private String message;

    public CuentaErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
