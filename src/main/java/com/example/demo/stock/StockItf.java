package com.example.demo.stock;

import java.util.List;

public interface StockItf {

    List<Stock> findAll();
    void addArticle(String nomArticle, int quantite);
    Stock save(Stock stock);
    Stock getArticleById(Long id);
    Stock findByNomArticle(String nomArticle);
}
