package com.xantrix.webapp.exception;

public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private String message = "Elemento Ricercato Non Trovato";
    public NotFoundException() {super();}
    public NotFoundException(String Message) {
        super(Message);
        this.message = Message;
    }

}
