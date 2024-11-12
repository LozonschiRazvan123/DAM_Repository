package org.example.Test;
import org.example.Model.ComandaAprovizionare;
import org.example.Model.ComandaProdus;
import org.example.Model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class  ComandaProdusTest {

    private ComandaProdus comandaProdus;
    private ComandaAprovizionare comandaAprovizionare;
    private Produs produs;

    @BeforeEach
    public void setUp() {
        //comandaAprovizionare = new ComandaAprovizionare();
        produs = new Produs();
        comandaProdus = new ComandaProdus(1L, 10, 20.5);
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
        assertEquals(comandaAprovizionare, comandaProdus.getComandaAprovizionare());
    }

    @Test
    public void testSetComandaAprovizionare() {
        ComandaAprovizionare nouaComanda = new ComandaAprovizionare();
        comandaProdus.setComandaAprovizionare(nouaComanda);
        assertEquals(nouaComanda, comandaProdus.getComandaAprovizionare());
    }

    @Test
    public void testGetProdus() {
        assertEquals(produs, comandaProdus.getProdus());
    }

    @Test
    public void testSetProdus() {
        Produs nouProdus = new Produs();
        comandaProdus.setProdus(nouProdus);
        assertEquals(nouProdus, comandaProdus.getProdus());
    }

    @Test
    public void testGetCantitate() {
        assertEquals(10, comandaProdus.getCantitate());
    }

    @Test
    public void testSetCantitate() {
        comandaProdus.setCantitate(15);
        assertEquals(15, comandaProdus.getCantitate());
    }

    @Test
    public void testGetPretUnitate() {
        assertEquals(20.5, comandaProdus.getPretUnitate());
    }

    @Test
    public void testSetPretUnitate() {
        comandaProdus.setPretUnitate(30.0);
        assertEquals(30.0, comandaProdus.getPretUnitate());
    }
}

