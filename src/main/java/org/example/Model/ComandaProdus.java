package org.example.Model;

import jakarta.persistence.*;

@Entity
public class ComandaProdus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ComandaAprovizionare comandaAprovizionare;

    @ManyToOne
    private Produs produs;

    private int cantitate;
    private double pretUnitate;

    public ComandaProdus(Long id, ComandaAprovizionare comandaAprovizionare, Produs produs, int cantitate, double pretUnitate) {
        this.id = id;
        this.comandaAprovizionare = comandaAprovizionare;
        this.produs = produs;
        this.cantitate = cantitate;
        this.pretUnitate = pretUnitate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComandaAprovizionare getComandaAprovizionare() {
        return comandaAprovizionare;
    }

    public void setComandaAprovizionare(ComandaAprovizionare comandaAprovizionare) {
        this.comandaAprovizionare = comandaAprovizionare;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public double getPretUnitate() {
        return pretUnitate;
    }

    public void setPretUnitate(double pretUnitate) {
        this.pretUnitate = pretUnitate;
    }
    // getters și setters
}
