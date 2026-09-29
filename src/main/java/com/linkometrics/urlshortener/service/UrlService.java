package com.linkometrics.urlshortener.service;

import com.linkometrics.urlshortener.entity.Url;
import com.linkometrics.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    //Spring sees this constructor and provides the UrlRepository object automatically.
    public UrlService(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }

    //Mapping of Java objects to database records.
    public Url saveUrl(String originalUrl)
    {
        Url url = new Url();  //Each url object is a row in the database.

        url.setOriginalUrl(originalUrl);

        String shortCode = UUID.randomUUID().toString().substring(0, 6); //Generating random short code of 6 characters.

        url.setShortCode(shortCode);

        return urlRepository.save(url); //Spring Data JPA takes this Java object and saves it to PostgreSQL.

    }


    //this code can be put into controller, but keeping it as a method here makes it modular to reuse this logic from anywhere, many times.
    public Url getByShortCode(String shortCode)
    {
        return urlRepository.findByShortCode(shortCode).orElseThrow(); //If the URL exists, give me the Url object. If it doesn't exist, throw an exception.
    }


}

