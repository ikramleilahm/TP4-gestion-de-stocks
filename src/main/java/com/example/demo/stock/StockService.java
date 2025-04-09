package com.example.demo.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements StockItf {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public void addArticle(String nomArticle, int quantite) {
        Stock stock = new Stock();
        stock.setNomArticle(nomArticle);
        stock.setQuantite(quantite);
        stockRepository.save(stock);
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock getArticleById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Stock findByNomArticle(String nomArticle) {
        return stockRepository.findByNomArticle(nomArticle);
    }
}
