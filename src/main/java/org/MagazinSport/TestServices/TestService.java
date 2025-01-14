package org.MagazinSport.TestServices;

import jakarta.transaction.Transactional;
import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.ComandaProdus;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Repository.ComandaProdusRepository;
import org.MagazinSport.Services.AlerteStocService;
import org.MagazinSport.Services.ComandaProdusService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestService {

    @Autowired
    private AlerteStocService alerteStocService;

    @BeforeEach
    void setUp() {
        // Dacă ai nevoie să adaugi date suplimentare înainte de testare, le poți adăuga aici.
    }

    @Test
    void testGetAllAlerteStoc() {
        List<AlerteStoc> alerte = alerteStocService.getAllAlerteStoc();
        assertNotNull(alerte, "Lista de alerte nu ar trebui să fie null.");
        assertFalse(alerte.isEmpty(), "Lista de alerte nu ar trebui să fie goală.");
        alerte.forEach(alerta -> System.out.println("Alerta Stoc ID: " + alerta.getIdAlerteStoc()));
    }


    @Autowired
    private ComandaProdusService comandaProdusService;

    @Autowired
    private ComandaProdusRepository comandaProdusRepository;


    @Test
    void testGetComandaProdusById() {
        Optional<ComandaProdus> comandaOpt = comandaProdusRepository.findAll().stream().findFirst();
        assertTrue(comandaOpt.isPresent(), "Ar trebui să existe cel puțin o ComandaProdus în baza de date.");

        ComandaProdus comanda = comandaProdusService.getComandaProdusById(comandaOpt.get().getId()).orElse(null);
        assertNotNull(comanda, "ComandaProdus ar trebui să fie găsită după ID.");
    }

    @Test
    void testUpdateComandaProdus() {
        Optional<ComandaProdus> comandaOpt = comandaProdusRepository.findAll().stream().findFirst();
        assertTrue(comandaOpt.isPresent(), "Ar trebui să existe cel puțin o ComandaProdus în baza de date.");

        ComandaProdus comandaDeActualizat = comandaOpt.get();
        comandaDeActualizat.setCantitate(10);
        comandaDeActualizat.setPretUnitate(200.0);

        ComandaProdus comandaActualizata = comandaProdusService.updateComandaProdus(comandaDeActualizat.getId(), comandaDeActualizat);
        assertNotNull(comandaActualizata, "ComandaProdus ar trebui să fie actualizată.");
        assertEquals(10, comandaActualizata.getCantitate());
        assertEquals(200.0, comandaActualizata.getPretUnitate());
    }

    @Test
    void testDeleteComandaProdus() {
        Optional<ComandaProdus> comandaOpt = comandaProdusRepository.findAll().stream().findFirst();
        assertTrue(comandaOpt.isPresent(), "Ar trebui să existe cel puțin o ComandaProdus în baza de date.");

        comandaProdusService.deleteComandaProdus(comandaOpt.get().getId());
        Optional<ComandaProdus> comandaDupaStergere = comandaProdusService.getComandaProdusById(comandaOpt.get().getId());
        assertTrue(comandaDupaStergere.isEmpty(), "ComandaProdus ar trebui să fie ștearsă.");
    }

    @Test
    void testGetComandaProduseByComandaId() {
        // Test custom query for ComandaAprovizionare ID
        // Asigură-te că există un ID valid pentru ComandaAprovizionare în baza de date
        Long comandaIdValid = 1L; // Modifică în funcție de ID-ul din setup
        List<ComandaProdus> comenzi = comandaProdusService.getComandaProduseByComandaId(comandaIdValid);
        assertNotNull(comenzi, "Lista de ComandaProdus ar trebui să fie găsită pentru ComandaAprovizionare.");
    }

    @Test
    void testGetComandaProduseByProdusId() {
        // Test custom query for Produs ID
        // Asigură-te că există un ID valid pentru Produs în baza de date
        Long produsIdValid = 1L; // Modifică în funcție de ID-ul din setup
        List<ComandaProdus> comenzi = comandaProdusService.getComandaProduseByProdusId(produsIdValid);
        assertNotNull(comenzi, "Lista de ComandaProdus ar trebui să fie găsită pentru Produs.");
    }
}
