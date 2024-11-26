package org.MagazinSport.Test;

import org.MagazinSport.Model.AlerteStoc;
import org.MagazinSport.Model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AlerteStocTest {

    private AlerteStoc alertaStoc;
    private Produs produs;

    @BeforeEach
    public void setUp() {
        produs = new Produs(1L, "Laptop", "Electronice", 2500.0, 1800.0, 10);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1); // Setăm data alertei în viitor
        alertaStoc = new AlerteStoc(1L, true, cal.getTime());
        alertaStoc.setProdus(produs);
    }

    @Test
    public void testGetIdAlerteStoc() {
        assertEquals(1L, alertaStoc.getIdAlerteStoc(), "ID-ul alertei ar trebui sa fie 1");
    }

    @Test
    public void testSetIdAlerteStoc() {
        alertaStoc.setIdAlerteStoc(2L);
        assertEquals(2L, alertaStoc.getIdAlerteStoc(), "ID-ul alertei ar trebui sa fie 2 după setare");
    }

    @Test
    public void testGetProdus() {
        assertEquals(produs, alertaStoc.getProdus(), "Produsul ar trebui să fie cel inițializat în setUp()");
    }

    @Test
    public void testSetProdus() {
        Produs newProdus = new Produs(2L, "Telefon", "Electronice", 1200.0, 900.0, 20);
        alertaStoc.setProdus(newProdus);
        assertEquals(newProdus, alertaStoc.getProdus(), "Produsul ar trebui să fie cel nou setat");
    }

    @Test
    public void testIsActivCuDataViitoare() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        alertaStoc.setDataAlerta(cal.getTime());
        alertaStoc.setActiv(true);

        assertTrue(alertaStoc.isValid(), "Alerta ar trebui să fie activă dacă data este în viitor");
    }

    @Test
    public void testIsActivCuDataCurenta() {
        alertaStoc.setDataAlerta(new Date());
        alertaStoc.setActiv(true);
        assertFalse(alertaStoc.isValid(), "Alerta ar trebui să fie activă dacă data este curentă");
    }

    @Test
    public void testIsActivCuDataTrecuta() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1); // Setăm data în trecut
        alertaStoc.setDataAlerta(cal.getTime());
        alertaStoc.setActiv(false);

        assertTrue(alertaStoc.isValid(), "Alerta ar trebui să fie inactivă dacă data este în trecut");
    }

    @Test
    public void testValidareAutomataInFunctieDeData() {
        // Testăm cu data viitoare și alertă activă
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5);
        alertaStoc.setDataAlerta(cal.getTime());
        alertaStoc.setActiv(true);
        assertTrue(alertaStoc.isValid(), "Alerta ar trebui să fie activă pentru o dată viitoare");

        // Testăm cu data curentă
        alertaStoc.setDataAlerta(new Date());
        alertaStoc.setActiv(true);
        assertFalse(alertaStoc.isValid(), "Alerta ar trebui să fie validă pentru data curentă");

        // Testăm cu data trecută și alertă inactivă
        cal.add(Calendar.DAY_OF_MONTH, -10);
        alertaStoc.setDataAlerta(cal.getTime());
        alertaStoc.setActiv(false);
        assertTrue(alertaStoc.isValid(), "Alerta ar trebui să fie inactivă pentru o dată trecută");
    }

    @Test
    public void testSetareDataInTrecutInvalid() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -10); // Setăm data în trecut
        alertaStoc.setDataAlerta(cal.getTime());

        assertFalse(alertaStoc.isValid(), "Alerta ar trebui să fie invalidă dacă data este în trecut și alertă activă");
    }

    @Test
    public void testAlertaStocToString() {
        String expectedString = "AlerteStoc [idAlerteStoc=" + alertaStoc.getIdAlerteStoc() +
                ", produs=" + alertaStoc.getProdus().getNume() +
                ", activ=" + alertaStoc.getActiv() +
                ", dataAlerta=" + alertaStoc.getDataAlerta() + "]";
        assertEquals(expectedString, alertaStoc.toString(), "Metoda toString ar trebui să returneze o reprezentare corectă");
    }
}
