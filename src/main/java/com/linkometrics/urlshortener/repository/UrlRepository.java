package com.linkometrics.urlshortener.repository;

import com.linkometrics.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//"I have a Url entity whose ID is a Long. Give me the database operations for it."
public interface UrlRepository extends JpaRepository<Url, Long> {

    //Optional gives us a way to represent both possibilities - Url found and not found.
    Optional<Url> findByShortCode(String shortCode);

}

