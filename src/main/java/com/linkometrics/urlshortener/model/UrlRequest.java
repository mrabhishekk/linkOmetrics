package com.linkometrics.urlshortener.model;

import jakarta.validation.constraints.NotBlank;

//this class represents the data coming from the user into the app
public class UrlRequest
{
    @NotBlank
    private String originalUrl;

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
         this.originalUrl = originalUrl;    //this.originalUrl is the private variable created above
    }
}

