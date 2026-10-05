package com.linkometrics.urlshortener.service;

import com.linkometrics.urlshortener.entity.Url;
import com.linkometrics.urlshortener.exception.InvalidUserUrlException;
import com.linkometrics.urlshortener.exception.UrlExpiredException;
import com.linkometrics.urlshortener.exception.UrlNotFoundException;
import com.linkometrics.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    //Spring sees this constructor and provides the UrlRepository object automatically.
    public UrlService(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }


    //Mapping of Java objects to database records.
    public Url saveUrl(String originalUrl, LocalDateTime expiresAt)
    {
        originalUrl = validateAndFormatUrl(originalUrl);

        Url url = new Url();  //Each url object is a row in the database.

        url.setOriginalUrl(originalUrl);

        url.setExpiresAt(expiresAt);

        urlRepository.save(url); //Spring Data JPA takes this Java object and saves it to PostgreSQL. //We also create the database id.

        String shortCode = encodeBase62(url.getId());

        url.setShortCode(shortCode);

        return urlRepository.save(url);

    }


    private String validateAndFormatUrl(String originalUrl)
    {
        if(originalUrl == null || originalUrl.isBlank())
        {
            throw new InvalidUserUrlException("Url cannot be empty.");     //@NotBlank catches the empty string before your service is called.
        }

        String url = originalUrl.trim(); //removes spaces before and after the url

        if(!url.startsWith("http://") && !url.startsWith("https://"))
        {
            url = "https://" + url;
        }

      try   //try contains code that might throw an exception
        {
            URI uri = new URI(url);   //if the url has an invalid URI structure, it can fail

            String host = uri.getHost();  //getHost() asks: Does this URI have a recognizable domain/host?

            if(host == null || !host.contains(".") || host.startsWith(".") || host.endsWith("."))
            {
                throw new InvalidUserUrlException("Invalid Url");  //the string "Invalid Url" will be shown by Exception Handler
            }

            return url;
        }
        catch (URISyntaxException e)
        {
            throw new InvalidUserUrlException("Invalid Url");
        }

    }


    public String encodeBase62(Long id)
    {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder result = new StringBuilder();

        while(id > 0)
        {
            int remainder = (int)(id % 62);

            result.append(characters.charAt(remainder));

            id = id / 62;
        }

        return result.reverse().toString();
    }


    //this code can be put into controller, but keeping it as a method here makes it modular to reuse this logic from anywhere, many times.
    public Url getByShortCode(String shortCode)
    {
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("Short Url not found.")); //If the URL exists, give me the Url object. If it doesn't exist, throw an exception.

        //url.getExpiresAt() == null means never expiring.
        if(url.getExpiresAt() != null && LocalDateTime.now().isAfter(url.getExpiresAt()))
        {
            throw new UrlExpiredException("Short Url has expired.");
        }

        url.incrementClicks();
        urlRepository.save(url);

        return url;
    }


}

