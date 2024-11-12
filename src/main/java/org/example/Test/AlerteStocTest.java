package org.example.Test;

import org.example.Model.AlerteStoc;
import org.example.Model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AlerteStocTest {

    private AlerteStoc alertaStoc;
    private Produs produs;

    @BeforeEach
    public void setUp() {
        produs = new Produs();
        alertaStoc = new AlerteStoc(1L, true, new Date());
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
        assertEquals(produs, alertaStoc.getProdus(), "Produsul ar trebui sa fie cel inițializat în setUp()");
    }

    @Test
    public void testSetProdus() {
        Produs newProdus = new Produs();
        alertaStoc.setProdus(newProdus);
        assertEquals(newProdus, alertaStoc.getProdus(), "Produsul ar trebui sa fie cel nou setat");
    }

    @Test
    public void testIsActiv() {
        assertTrue(alertaStoc.isActiv(), "Alerta ar trebui sa fie activă");
    }

    @Test
    public void testSetActiv() {
        alertaStoc.setActiv(false);
        assertFalse(alertaStoc.isActiv(), "Alerta ar trebui sa fie inactiva după setare");
    }

    @Test
    public void testGetDataAlerta() {
        Date dataAlerta = alertaStoc.getDataAlerta();
        assertNotNull(dataAlerta, "Data alertei nu ar trebui sa fie null");
    }

    @Test
    public void testSetDataAlerta() {
        Date newDataAlerta = new Date();
        alertaStoc.setDataAlerta(newDataAlerta);
        assertEquals(newDataAlerta, alertaStoc.getDataAlerta(), "Data alertei ar trebui sa fie cea nou setata");
    }
}
