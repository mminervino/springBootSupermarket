package com.xantrix.webapp.exception;

public class DuplicateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String messaggio = "Elemento già presente";
    public DuplicateException() {super();}
    public DuplicateException(String Message) {
        super(Message);
        this.messaggio = Message;
    }
}
