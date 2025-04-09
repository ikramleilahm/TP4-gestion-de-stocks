package com.example.demo.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/home")
    public ModelAndView home() {
        Iterable<Stock> stocks = stockService.findAll();
        ModelAndView modelAndView = new ModelAndView("stocks/home");
        modelAndView.addObject("stocks", stocks);
        return modelAndView;
    }

    @PostMapping("/home/reapprovisionner")
    public String reapprovisionner() {
        List<String> nomsArticles = List.of("Livre", "Ordinateur", "Clavier", "Souris", "Airpods", "Écran", "Casque");

        for (String nom : nomsArticles) {
            Optional<Stock> stockOpt = Optional.ofNullable(stockService.findByNomArticle(nom));
            Stock stock;
            if (stockOpt.isPresent()) {
                stock = stockOpt.get();
                stock.setQuantite(stock.getQuantite() + 10);
            } else {
                stock = new Stock(nom, 10);
            }
            stockService.save(stock);
        }

        return "redirect:/stocks/home";
    }
}
