package org.example.Model;

import jakarta.persistence.*;

@Entity
public class VanzareProdus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vanzare vanzare;

    @ManyToOne
    private Produs produs;

    private int cantitate;
    private double pretUnitate;

    public VanzareProdus(Long id, Vanzare vanzare, Produs produs, int cantitate, double pretUnitate) {
        this.id = id;
        this.vanzare = vanzare;
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

    public Vanzare getVanzare() {
        return vanzare;
    }

    public void setVanzare(Vanzare vanzare) {
        this.vanzare = vanzare;
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
}

