package com.linkometrics.urlshortener.exception;

public class InvalidUserUrlException extends RuntimeException
{
    public InvalidUserUrlException(String message)
    {
        super(message);
    }
}
