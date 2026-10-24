package com.linkometrics.urlshortener.exception;

public class InvalidPaginationException extends RuntimeException
{
    public InvalidPaginationException(String message) //The constructor accepts an error message.
    {
         super(message);
    }

}
