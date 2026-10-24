package com.linkometrics.urlshortener.controller;

import com.linkometrics.urlshortener.entity.Url;
import com.linkometrics.urlshortener.exception.InvalidPaginationException;
import com.linkometrics.urlshortener.model.UrlRequest;
import com.linkometrics.urlshortener.model.UrlStatsResponse;
import com.linkometrics.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController  //@RestController tells this class contains APIs that should receive HTTP requests.
//@RequestMapping("/abhi")
public class UrlController  //controller is mainly responsible for building the HTTP response not business logic
{

    @GetMapping("/hello")    //When someone sends a GET request to /hello, execute this method.
    public String hello()
    {
        return "Url Shortener is working.";  //The returned String becomes the HTTP response.
    }


    private final UrlService urlService;

    //The constructor itself doesn't automatically find UrlService.
    //The constructor provides the place where UrlService can be assigned, and Spring automatically calls the constructor and gives it the UrlService object.
    //So, constructor helps in auto-assigning.
    public UrlController(UrlService urlService)
    {
        this.urlService = urlService;
    }


    @PostMapping("/shorten")
    public Url shortenUrl(@Valid @RequestBody UrlRequest request) //@RequestBody converts JSON → UrlRequest
    {
        return urlService.saveUrl(request.getOriginalUrl(), request.getExpiresAt());
    }


    //When someone sends a GET request containing a short code in the URL, take that short code, and return an HTTP response that will redirect them.
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) //@PathVariable = Take the value from {shortCode} in the URL and put it into the Java variable shortCode.
    {
        Url url = urlService.recordClicks(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND) //FOUND means HTTP status 302 - resource temporarily moved - redirect
                .location(URI.create(url.getOriginalUrl())) //where to redirect, URI.create() is basically converting your URL String into the URI object needed for the redirect response.
                .build();
    }


    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse getStats(@PathVariable String shortCode)
    {
        return urlService.getStats(shortCode);
    }


    @DeleteMapping("{shortCode}")   //maps HTTP DELETE requests to this method
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode)
    {
        urlService.deleteByShortCode(shortCode);

        return ResponseEntity.noContent().build(); //returns HTTP 204 No Content, meaning the deletion succeeded
    }


    @GetMapping("/urls")
    public Page<Url> getAllUrls(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) //@RequestParam reads values from the URL query parameters.
    {
        if(page < 0)
        {
            throw new InvalidPaginationException("Page number cannot be negative");
        }

        if(size < 1 || size > 10)
        {
            throw new InvalidPaginationException("Page size must be between 1 and 10");
        }

        Pageable pageable = PageRequest.of(page, size);

        return urlService.getAllUrls(pageable);
    }

}
