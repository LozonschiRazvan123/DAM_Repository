package org.MagazinSport.SeedData;

import org.MagazinSport.Model.*;
import org.MagazinSport.Repository.*;
import org.MagazinSport.Role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.*;

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
            VanzareProdusRepository vanzareProdusRepository,
            UserRepository userRepository)
    {
        return args -> {



                User user = new User("user2025", new BCryptPasswordEncoder().encode("feaa2025"),
                        new HashSet<>(Set.of(Role.USER)), "user@example.com");

                User admin = new User("admin2025", new BCryptPasswordEncoder().encode("feaa2025"),
                        new HashSet<>(Set.of(Role.ADMIN)), "admin@example.com");

                userRepository.save(user);
                userRepository.save(admin);

            if (furnizorRepository.count() == 0) {
                Random random = new Random();

                Furnizor furnizor1 = furnizorRepository.save(new Furnizor("Furnizor1", "Adresa1", "+40712345678", "email1@example.com"));
                Furnizor furnizor2 = furnizorRepository.save(new Furnizor("Furnizor2", "Adresa2", "+40798765432", "email2@example.com"));
                Furnizor furnizor3 = furnizorRepository.save(new Furnizor("Furnizor3", "Adresa3", "+40765432189", "email3@example.com"));

                Produs produs1 = produsRepository.save(new Produs("Manusi", "Haine", 50.0, 10.0, 15, furnizor1));
                Produs produs2 = produsRepository.save(new Produs("Ghete", "Incaltaminte", 100.0, 20.0, 5, furnizor2));
                Produs produs3 = produsRepository.save(new Produs("Sapca", "Accesorii", 30.0, 5.0, 10, furnizor1));
                Produs produs4 = produsRepository.save(new Produs("Tricou", "Haine", 25.0, 7.0, 20, furnizor3));
                Produs produs5 = produsRepository.save(new Produs("Hanorac", "Haine", 75.0, 30.0, 8, furnizor3));

                Stoc stoc1 = stocRepository.save(new Stoc(produs1, 10, 5, new Date()));
                Stoc stoc2 = stocRepository.save(new Stoc(produs2, 2, 5, new Date())); // Declanșează alertă de stoc
                Stoc stoc3 = stocRepository.save(new Stoc(produs3, 15, 3, new Date()));
                Stoc stoc4 = stocRepository.save(new Stoc(produs4, 5, 10, new Date()));
                Stoc stoc5 = stocRepository.save(new Stoc(produs5, 1, 3, new Date())); // Declanșează alertă de stoc

                alerteStocRepository.save(new AlerteStoc(produs2, true, new Date())); // Alertă activă
                alerteStocRepository.save(new AlerteStoc(produs5, true, new Date())); // Alertă activă

                ComandaAprovizionare comanda1 = comandaAprovizionareRepository.save(
                        new ComandaAprovizionare(new Date(), new Date(System.currentTimeMillis() + 86400000L), "Pending", furnizor1)
                );
                ComandaAprovizionare comanda2 = comandaAprovizionareRepository.save(
                        new ComandaAprovizionare(new Date(), new Date(System.currentTimeMillis() + 172800000L), "Completed", furnizor2)
                );
                ComandaAprovizionare comanda3 = comandaAprovizionareRepository.save(
                        new ComandaAprovizionare(new Date(), new Date(System.currentTimeMillis() + 259200000L), "Pending", furnizor3)
                );

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dataComanda1 = dateFormat.parse("10/12/2024");
                Date dataComanda2 = dateFormat.parse("01/12/2024");
                Date dataComanda3 = dateFormat.parse("15/11/2024");

                comandaProdusRepository.save(new ComandaProdus(comanda1, produs1, 10, 15.0, dataComanda1));
                comandaProdusRepository.save(new ComandaProdus(comanda2, produs2, 20, 30.0, dataComanda2));
                comandaProdusRepository.save(new ComandaProdus(comanda3, produs5, 5, 40.0, dataComanda3));

                Vanzare vanzare1 = vanzareRepository.save(new Vanzare(new Date(), 500.0));
                Vanzare vanzare2 = vanzareRepository.save(new Vanzare(new Date(System.currentTimeMillis() - 86400000L), 750.0));

                vanzareProdusRepository.save(new VanzareProdus(vanzare1, produs1, 5, 50.0));
                vanzareProdusRepository.save(new VanzareProdus(vanzare1, produs3, 3, 30.0));
                vanzareProdusRepository.save(new VanzareProdus(vanzare2, produs2, 10, 100.0));
                vanzareProdusRepository.save(new VanzareProdus(vanzare2, produs4, 8, 25.0));

                // Adăugare alte 15 vânzări suplimentare cu sume aleatorii și date între 2020 și 2024
                for (int i = 3; i <= 17; i++) {
                    double total = 300.0 + random.nextDouble() * 700.0; // Total între 300 și 1000

                    // Generare dată între 2020 și 2024
                    Calendar calendar = Calendar.getInstance();
                    int year = 2020 + random.nextInt(5); // An între 2020 și 2024
                    int dayOfYear = 1 + random.nextInt(365); // Ziua anului
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
                    Date randomDate = calendar.getTime();

                    Vanzare vanzare = vanzareRepository.save(
                            new Vanzare(randomDate, total)
                    );

                    vanzareProdusRepository.save(new VanzareProdus(vanzare, produs1, 2 * i, 50.0));
                    vanzareProdusRepository.save(new VanzareProdus(vanzare, produs3, 1 * i, 30.0));
                }
            }
        };
    }
}
