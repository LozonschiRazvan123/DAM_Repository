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
            // Verifică dacă baza de date este deja populată
            if (furnizorRepository.count() > 0 || produsRepository.count() > 0) {
                System.out.println("Exists");
                return;
            }

            System.out.println("Popularea bazei de date...");

            Furnizor furnizor1 = furnizorRepository.save(new Furnizor("Furnizor1", "Adresa1", "+40712345678", "email1@example.com"));
            Furnizor furnizor2 = furnizorRepository.save(new Furnizor("Furnizor2", "Adresa2", "+40798765432", "email2@example.com"));

            Produs produs1 = produsRepository.save(new Produs("Manusi", "Haine", 50.0, 10.0, 10, furnizor1));
            Produs produs2 = produsRepository.save(new Produs("Ghete", "Haine", 100.0, 5.0, 5, furnizor2));

            Stoc stoc1 = new Stoc(produs1, 10, 5, new Date());
            Stoc stoc2 = new Stoc(produs2, 5, 2, new Date());
            stocRepository.saveAll(List.of(stoc1, stoc2));

            AlerteStoc alerta1 = new AlerteStoc(produs1, true, new Date());
            alerteStocRepository.save(alerta1);

            ComandaAprovizionare comanda1 = comandaAprovizionareRepository.save(
                    new ComandaAprovizionare(new Date(), new Date(System.currentTimeMillis() + 86400000L), "Pending", furnizor1)
            );

            comandaProdusRepository.save(new ComandaProdus(comanda1, produs1, 2, 50.0));

            Vanzare vanzare1 = vanzareRepository.save(new Vanzare(new Date(), 200.0));
            vanzareProdusRepository.save(new VanzareProdus(vanzare1, produs1, 2, 100.0));
        };
    }
}
