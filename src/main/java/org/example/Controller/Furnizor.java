package org.example.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Furnizor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nume;
    private String adresa;
    private String telefon;
    private String email;

    @OneToMany(mappedBy = "furnizor")
    private List<org.example.Model.Produs> produse;

    // getters și setters
}