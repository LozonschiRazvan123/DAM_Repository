package org.example.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Furnizor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFurnizor;

    private String nume;
    private String adresa;
    private String telefon;
    private String email;

    @OneToMany(mappedBy = "furnizor")
    private List<Produs> produse;

    // getters și setters
}
