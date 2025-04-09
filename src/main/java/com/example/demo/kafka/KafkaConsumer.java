package com.example.demo.kafka;

import com.example.demo.stock.Stock;
import com.example.demo.stock.StockService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private StockService stockService;

    @KafkaListener(topics = "my-first-topic", groupId = "my-first-group")
    public void consume(ConsumerRecord<String, String> record) {
        String message = record.value();
        String[] lignes = message.split("\\n");

        for (String ligne : lignes) {
            String[] parts = ligne.trim().split(" ");
            if (parts.length < 2) continue;

            String nomArticle = parts[0];
            int quantiteCommandee;
            try {
                quantiteCommandee = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                LOGGER.warn("Quantité invalide pour l'article {} : {}", nomArticle, parts[1]);
                continue;
            }

            Optional<Stock> stockOpt = Optional.ofNullable(stockService.findByNomArticle(nomArticle));
            if (stockOpt.isPresent()) {
                Stock stock = stockOpt.get();
                int nouvelleQuantite = stock.getQuantite() - quantiteCommandee;

                if (nouvelleQuantite < 0) {
                    nouvelleQuantite = 0;
                    LOGGER.warn("Stock insuffisant pour l'article {}. Commandé : {}, Disponible : {}",
                            nomArticle, quantiteCommandee, stock.getQuantite());
                }
                stock.setQuantite(nouvelleQuantite);
                LOGGER.info("Stock mis à jour pour l'article '{}': Nouvelle quantité = {}", nomArticle, nouvelleQuantite);
            } else {
                LOGGER.warn("Article '{}' non trouvé dans le stock", nomArticle);
            }
        }
    }
}