package com.linkometrics.urlshortener.service;

import com.linkometrics.urlshortener.entity.Url;
import com.linkometrics.urlshortener.exception.UrlNotFoundException;
import com.linkometrics.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

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

        urlRepository.save(url); //Spring Data JPA takes this Java object and saves it to PostgreSQL. //We also create the database id.

        String shortCode = encodeBase62(url.getId());

        url.setShortCode(shortCode);

        return urlRepository.save(url);

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
        return urlRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException("Short Url not found.")); //If the URL exists, give me the Url object. If it doesn't exist, throw an exception.
    }


}

