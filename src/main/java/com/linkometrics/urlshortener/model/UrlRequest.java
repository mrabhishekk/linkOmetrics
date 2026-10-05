package com.linkometrics.urlshortener.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

//this class represents the data coming from the user into the app, which gets turn into an object and get used by the controller //think of it as all the parameters the shortenUrl( ) will receive
public class UrlRequest
{
    @NotBlank
    private String originalUrl;

    private LocalDateTime expiresAt;


    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
         this.originalUrl = originalUrl;    //this.originalUrl is the private variable created above
    }


    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt)
    {
        this.expiresAt = expiresAt;
    }
}

