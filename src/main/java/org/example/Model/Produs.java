package org.example.Model;

import jakarta.persistence.*;

@Entity
public class Produs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdus;

    private String nume;
    private String categorie;
    private double pretVanzare;
    private double pretAchizitie;
    private int stoc;

    public Produs(Long idProdus, String nume, String categorie, double pretVanzare, double pretAchizitie, int stoc, Furnizor furnizor) {
        this.idProdus = idProdus;
        this.nume = nume;
        this.categorie = categorie;
        this.pretVanzare = pretVanzare;
        this.pretAchizitie = pretAchizitie;
        this.stoc = stoc;
        this.furnizor = furnizor;
    }
    public Produs() {

    }


    public Long getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(Long idProdus) {
        this.idProdus = idProdus;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPretVanzare() {
        return pretVanzare;
    }

    public void setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public double getPretAchizitie() {
        return pretAchizitie;
    }

    public void setPretAchizitie(double pretAchizitie) {
        this.pretAchizitie = pretAchizitie;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    @ManyToOne
    private Furnizor furnizor;

    // getters și setters
}
