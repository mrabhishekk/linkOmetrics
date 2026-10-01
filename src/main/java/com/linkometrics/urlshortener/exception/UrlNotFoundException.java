package com.linkometrics.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message)
    {
        super(message);
    }

}
