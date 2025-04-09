package com.example.demo.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    private String nomArticle;
    private int quantite;

    public Stock(String nom, int quantite) {
        this.nomArticle = nom;
        this.quantite = quantite;
    }

    public Stock() {
        // Constructeur par défaut
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNomArticle() {
        return nomArticle;
    }
    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}
