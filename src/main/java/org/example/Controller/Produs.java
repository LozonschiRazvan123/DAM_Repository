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

    @ManyToOne
    private org.example.Model.Furnizor furnizor;

    // getters și setters
}
