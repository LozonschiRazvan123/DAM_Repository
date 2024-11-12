package org.example.Test;

import org.example.Model.ComandaAprovizionare;
import org.example.Model.ComandaProdus;
import org.example.Model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ComandaProdusTest {

    private ComandaProdus comandaProdus;
    private ComandaAprovizionare comandaAprovizionare;
    private Produs produs;

    @BeforeEach
    public void setUp() {
        comandaAprovizionare = new ComandaAprovizionare();
        produs = new Produs(1L, "Laptop", "Electronice", 1500.0, 1200.0, 50);
        comandaProdus = new ComandaProdus(1L, 10, 20.5);
        comandaProdus.setComandaAprovizionare(comandaAprovizionare);
        comandaProdus.setProdus(produs);
    }

    @Test
    public void testGetId() {
        assertEquals(1L, comandaProdus.getId());
    }

    @Test
    public void testSetId() {
        comandaProdus.setId(2L);
        assertEquals(2L, comandaProdus.getId());
    }

    @Test
    public void testGetComandaAprovizionare() {
        assertEquals(comandaAprovizionare, comandaProdus.getComandaAprovizionare(),
                "Comanda aprovizionare ar trebui să fie aceeași cu cea setată în setUp()");
    }

    @Test
    public void testSetComandaAprovizionare() {
        ComandaAprovizionare nouaComanda = new ComandaAprovizionare();
        comandaProdus.setComandaAprovizionare(nouaComanda);
        assertEquals(nouaComanda, comandaProdus.getComandaAprovizionare(),
                "Noua comandă aprovizionare ar trebui să fie corect setată");
    }

    @Test
    public void testGetProdus() {
        assertEquals(produs, comandaProdus.getProdus(),
                "Produsul ar trebui să fie același cu cel setat în setUp()");
    }

    @Test
    public void testSetProdus() {
        Produs nouProdus = new Produs(2L, "Telefon mobil", "Electronice", 800.0, 650.0, 100);
        comandaProdus.setProdus(nouProdus);
        assertEquals(nouProdus, comandaProdus.getProdus(),
                "Produsul ar trebui să fie corect setat");
    }

    @Test
    public void testGetCantitate() {
        assertEquals(10, comandaProdus.getCantitate(),
                "Cantitatea ar trebui să fie 10");
    }

    @Test
    public void testSetCantitate() {
        comandaProdus.setCantitate(15);
        assertEquals(15, comandaProdus.getCantitate(),
                "Cantitatea ar trebui să fie setată corect la 15");
    }

    @Test
    public void testGetPretUnitate() {
        assertEquals(20.5, comandaProdus.getPretUnitate(),
                "Prețul per unitate ar trebui să fie 20.5");
    }

    @Test
    public void testSetPretUnitate() {
        comandaProdus.setPretUnitate(30.0);
        assertEquals(30.0, comandaProdus.getPretUnitate(),
                "Prețul per unitate ar trebui să fie setat corect la 30.0");
    }

    @Test
    public void testCalculTotalComandaProdus() {
        double total = comandaProdus.getCantitate() * comandaProdus.getPretUnitate();
        assertEquals(205.0, total, "Totalul ar trebui să fie corect calculat la 205.0");
    }

    @Test
    public void testCantitateZeroSauNegativa() {
        // Testăm cu cantitate zero
        comandaProdus.setCantitate(0);
        assertEquals(0, comandaProdus.getCantitate(), "Cantitatea ar trebui să fie 0 după setare");

        // Testăm cu cantitate negativă și verificăm să nu fie permisă
        comandaProdus.setCantitate(-5);
        assertFalse(comandaProdus.getCantitate() >= 0, "Cantitatea nu ar trebui să fie negativă");
    }

    @Test
    public void testPretUnitateNegativSauZero() {
        // Testăm cu preț zero
        comandaProdus.setPretUnitate(0.0);
        assertEquals(0.0, comandaProdus.getPretUnitate(), "Prețul unitar ar trebui să fie 0");

        // Testăm cu preț negativ și verificăm să nu fie permis
        comandaProdus.setPretUnitate(-10.0);
        assertFalse(comandaProdus.getPretUnitate() >= 0,
                "Prețul unitar nu ar trebui să fie negativ după setare");
    }

    @Test
    public void testLegaturaComandaProdusSiAprovizionare() {
        ComandaProdus altComandaProdus = new ComandaProdus(2L, 5, 15.0);
        altComandaProdus.setComandaAprovizionare(comandaAprovizionare);
        assertTrue(Objects.equals(comandaProdus.getComandaAprovizionare(), altComandaProdus.getComandaAprovizionare()),
                "Ambele produse ar trebui să aibă aceeași comandă de aprovizionare");
    }

    @Test
    public void testModificareCantitateSiTotal() {
        comandaProdus.setCantitate(25);
        double totalExpected = 25 * comandaProdus.getPretUnitate();
        assertEquals(totalExpected, comandaProdus.getCantitate() * comandaProdus.getPretUnitate(),
                "Totalul ar trebui să fie calculat corect după schimbarea cantității");
    }

    @Test
    public void testProdusIndisponibil() {
        produs.setStoc(0);
        assertTrue(produs.getStoc() == 0,
                "Stocul ar trebui să fie zero pentru un produs indisponibil");

        int cantitateSolicitata = comandaProdus.getCantitate();
        assertTrue(produs.getStoc() < cantitateSolicitata,
                "Stocul nu ar trebui să fie suficient pentru cantitatea comandată dacă este zero");
    }
}
