package org.MagazinSport.SeedData;

import org.MagazinSport.Model.*;
import org.MagazinSport.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner populateDatabase(
            ProdusRepository produsRepository,
            FurnizorRepository furnizorRepository,
            AlerteStocRepository alerteStocRepository,
            ComandaAprovizionareRepository comandaAprovizionareRepository,
            ComandaProdusRepository comandaProdusRepository,
            StocRepository stocRepository,
            VanzareRepository vanzareRepository,
            VanzareProdusRepository vanzareProdusRepository
    ) {
        return args -> {
            // Șterge toate datele existente
            alerteStocRepository.deleteAllInBatch();
            comandaProdusRepository.deleteAllInBatch();
            comandaAprovizionareRepository.deleteAllInBatch();
            vanzareProdusRepository.deleteAllInBatch();
            vanzareRepository.deleteAllInBatch();
            stocRepository.deleteAllInBatch();
            produsRepository.deleteAllInBatch();
            furnizorRepository.deleteAllInBatch();

            System.out.println("Popularea bazei de date...");

            // Populare Furnizor
            Furnizor furnizor1 = furnizorRepository.save(new Furnizor("Furnizor1", "Adresa1", "+40712345678", "email1@example.com"));
            Furnizor furnizor2 = furnizorRepository.save(new Furnizor("Furnizor2", "Adresa2", "+40798765432", "email2@example.com"));

            // Populare Produs (salvare imediată)
            Produs produs1 = produsRepository.save(new Produs("Manusi", "Haine", 50.0, 10.0, 10, furnizor1));
            Produs produs2 = produsRepository.save(new Produs("Ghete", "Haine", 100.0, 5.0, 5, furnizor2));

            // Reatașare produs din baza de date (obligatoriu pentru a fi managed)
            produs1 = produsRepository.findById(produs1.getIdProdus()).orElseThrow();
            produs2 = produsRepository.findById(produs2.getIdProdus()).orElseThrow();

            // Asociază produsele la stoc și salvează stocurile
            Stoc stoc1 = new Stoc(produs1, 10, 5, new Date());
            Stoc stoc2 = new Stoc(produs2, 5, 2, new Date());
            stocRepository.saveAll(List.of(stoc1, stoc2));

            // Reatașare produs din baza de date pentru a fi managed
            produs1 = produsRepository.findById(produs1.getIdProdus()).orElseThrow();
            produs2 = produsRepository.findById(produs2.getIdProdus()).orElseThrow();

// Creare și salvare AlerteStoc
            AlerteStoc alerta1 = new AlerteStoc(produs1, true, new Date());
            alerteStocRepository.save(alerta1);

            // Populare ComandaAprovizionare și ComandaProdus
            ComandaAprovizionare comanda1 = comandaAprovizionareRepository.save(
                    new ComandaAprovizionare(new Date(), new Date(System.currentTimeMillis() + 86400000L), "Pending", furnizor1)
            );

            ComandaProdus comandaProdus1 = comandaProdusRepository.save(new ComandaProdus(comanda1, produs1, 2, 50.0));

            // Populare Vanzare și VanzareProdus
            Vanzare vanzare1 = vanzareRepository.save(new Vanzare(new Date(), 200.0));
            VanzareProdus vanzareProdus1 = vanzareProdusRepository.save(new VanzareProdus(vanzare1, produs1, 2, 100.0));
        };

    }
}
