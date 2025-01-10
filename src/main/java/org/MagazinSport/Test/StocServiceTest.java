package org.MagazinSport.Test;

import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Model.Produs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StocServiceTest {

    private Produs produs;

    @BeforeEach
    public void setUp() {
        Furnizor furnizor = new Furnizor();
        furnizor.setNume("Furnizor Test");
        //produs = new Produs(1L, "Minge fotbal", "Sport", 50.0, 30.0, 100);
    }

    @Test
    public void testGetterAndSetter() {
        Assertions.assertEquals(1L, produs.getIdProdus());
        Assertions.assertEquals("Minge fotbal", produs.getNume());
        Assertions.assertEquals("Sport", produs.getCategorie());
        Assertions.assertEquals(50.0, produs.getPretVanzare());
        Assertions.assertEquals(30.0, produs.getPretAchizitie());
        Assertions.assertEquals(100, produs.getStoc());

        // Testăm setter-ele cu valori noi
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
    public void testCalculProfitMarginalPeCantitate() {
        int cantitate = 10;
        double profitMarginal = (produs.getPretVanzare() - produs.getPretAchizitie()) * cantitate;
        Assertions.assertEquals(200.0, profitMarginal, "Profitul marginal pentru 10 unități ar trebui să fie 200.0");
    }

    @Test
    public void testVerificareStocSuficient() {
        int cantitateSolicitata = 50;
        Assertions.assertTrue(produs.getStoc() >= cantitateSolicitata,
                "Stocul ar trebui să fie suficient pentru cerere");

        cantitateSolicitata = 150;
        Assertions.assertFalse(produs.getStoc() >= cantitateSolicitata,
                "Stocul nu ar trebui să fie suficient pentru cerere");
    }

    @Test
    public void testReducereStocDupaVanzare() {
        int cantitateVanduta = 30;
        produs.setStoc(produs.getStoc() - cantitateVanduta);
        Assertions.assertEquals(70, produs.getStoc(),
                "Stocul ar trebui să fie redus la 70 după vânzarea a 30 de unități");
    }

    @Test
    public void testProfitNegativInCazDePretDeVanzareMaiMic() {
        produs.setPretVanzare(25.0);
        double profit = produs.getPretVanzare() - produs.getPretAchizitie();
        Assertions.assertTrue(profit < 0, "Profitul ar trebui să fie negativ în cazul unui preț de vânzare mai mic decât costul de achiziție");
    }

    @Test
    public void testModificarePretVanzareSiAchizitie() {
        produs.setPretVanzare(60.0);
        produs.setPretAchizitie(40.0);
        Assertions.assertEquals(60.0, produs.getPretVanzare(), "Prețul de vânzare ar trebui să fie setat la 60.0");
        Assertions.assertEquals(40.0, produs.getPretAchizitie(), "Prețul de achiziție ar trebui să fie setat la 40.0");
    }

    @Test
    public void testPretVanzareZeroSauNegativ() {
        produs.setPretVanzare(0.0);
        Assertions.assertTrue(produs.getPretVanzare() >= 0, "Prețul de vânzare nu ar trebui să fie negativ");

        produs.setPretVanzare(-10.0);
        Assertions.assertFalse(produs.getPretVanzare() >= 0, "Prețul de vânzare nu ar trebui să fie negativ, chiar și după încercarea de setare la valoare negativă");
    }

    @Test
    public void testCalculProfitInCazulStoculuiGol() {
        produs.setStoc(0);
        int cantitateSolicitata = 10;
        Assertions.assertFalse(produs.getStoc() >= cantitateSolicitata, "Stocul ar trebui să fie insuficient pentru orice cerere atunci când este 0");
    }
}
