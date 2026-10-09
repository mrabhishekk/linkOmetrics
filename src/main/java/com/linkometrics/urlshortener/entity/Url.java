package com.linkometrics.urlshortener.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class Url {      //this class represents the data stored in our database

    @Id
    private Long id;

    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode;

    private long clicks = 0;

    private LocalDateTime expiresAt;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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


    public LocalDateTime getExpiresAt()
    {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt)
    {
        this.expiresAt = expiresAt;
    }



}

