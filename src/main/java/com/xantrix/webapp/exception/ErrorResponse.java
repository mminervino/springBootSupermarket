package com.xantrix.webapp.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private String messaggio;
    private int codice;
    private Date data = new Date();
}