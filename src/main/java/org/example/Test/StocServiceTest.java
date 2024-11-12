package org.example.Test;

import org.example.Model.Furnizor;
import org.example.Model.Produs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StocServiceTest {

    private Produs produs;

    @BeforeEach
    public void setUp() {
        Furnizor furnizor = new Furnizor();
        furnizor.setNume("Furnizor Test");
        produs = new Produs(1L, "Minge fotbal", "Sport", 50.0, 30.0, 100);
    }

    @Test
    public void testGetterAndSetter() {
        Assertions.assertEquals(1L, produs.getIdProdus());
        Assertions.assertEquals("Minge fotbal", produs.getNume());
        Assertions.assertEquals("Sport", produs.getCategorie());
        Assertions.assertEquals(50.0, produs.getPretVanzare());
        Assertions.assertEquals(30.0, produs.getPretAchizitie());
        Assertions.assertEquals(100, produs.getStoc());

        // Testăm setter-ele
        produs.setNume("Minge baschet");
        produs.setCategorie("Sporturi de sală");
        produs.setPretVanzare(55.0);
        produs.setPretAchizitie(35.0);
        produs.setStoc(120);

        Assertions.assertEquals("Minge baschet", produs.getNume());
        Assertions.assertEquals("Sporturi de sală", produs.getCategorie());
        Assertions.assertEquals(55.0, produs.getPretVanzare());
        Assertions.assertEquals(35.0, produs.getPretAchizitie());
        Assertions.assertEquals(120, produs.getStoc());
    }

    @Test
    public void testConstructorFaraArgumente() {
        Produs produsNou = new Produs();
        produsNou.setIdProdus(2L);
        produsNou.setNume("Ghete fotbal");
        produsNou.setCategorie("Încălțăminte");
        produsNou.setPretVanzare(120.0);
        produsNou.setPretAchizitie(70.0);
        produsNou.setStoc(50);

        Assertions.assertEquals(2L, produsNou.getIdProdus());
        Assertions.assertEquals("Ghete fotbal", produsNou.getNume());
        Assertions.assertEquals("Încălțăminte", produsNou.getCategorie());
        Assertions.assertEquals(120.0, produsNou.getPretVanzare());
        Assertions.assertEquals(70.0, produsNou.getPretAchizitie());
        Assertions.assertEquals(50, produsNou.getStoc());
    }

    @Test
    public void testCalculProfitProdus() {
        double profit = produs.getPretVanzare() - produs.getPretAchizitie();
        Assertions.assertEquals(20.0, profit, "Profitul ar trebui să fie 20.0");
    } 

    @Test
    public void testVerificareStocSuficient() {
        // Test pentru o cantitate care poate fi acoperită din stoc
        int cantitateSolicitata = 50;
        Assertions.assertTrue(produs.getStoc() >= cantitateSolicitata,
                "Stocul ar trebui să fie suficient pentru cerere");

        // Test pentru o cantitate mai mare decât stocul
        cantitateSolicitata = 150;
        Assertions.assertFalse(produs.getStoc() >= cantitateSolicitata,
                "Stocul nu ar trebui să fie suficient pentru cerere");
    }
}
