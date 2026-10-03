package com.linkometrics.urlshortener.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class Url {      //this class represents data that is stored in our database

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode;

    private long clicks = 0;


    public Long getId()
    {
        return id;
    }


    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl = originalUrl;
    }


    public String getShortCode()
    {
        return shortCode;
    }

    public void setShortCode(String shortCode)
    {
        this.shortCode = shortCode;
    }


    public long getClicks()
    {
        return clicks;
    }

    public void incrementClicks()
    {
        clicks++;
    }


}

