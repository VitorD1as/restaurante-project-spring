package com.vitor.restaurante_project.exception;

public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}