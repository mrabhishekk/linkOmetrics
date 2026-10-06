package com.linkometrics.urlshortener.model;

import com.linkometrics.urlshortener.entity.Url;

import java.time.LocalDateTime;

public class UrlStatsResponse
{
    private String shortCode;
    private String originalUrl;
    private long clicks;
    private LocalDateTime expiresAt;

    //constructor for auto-initialzing/auto-assigning values
    public UrlStatsResponse(Url url)
    {
        this.shortCode = url.getShortCode();
        this.originalUrl = url.getOriginalUrl();
        this.clicks = url.getClicks();
        this.expiresAt = url.getExpiresAt();
    }


    public String getShortCode()
    {
        return shortCode;
    }

    public String getOriginalUrl()
    {
        return originalUrl;
    }

    public long getClicks()
    {
        return clicks;
    }

    public LocalDateTime getExpiresAt()
    {
        return expiresAt;
    }



}
