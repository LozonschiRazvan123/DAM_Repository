package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ComandaAprovizionare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataComanda;
    private Date dataLivrareEstimata;
    private String status;

    @ManyToOne
    private Furnizor furnizor;

    @OneToMany(mappedBy = "comandaAprovizionare", cascade = CascadeType.ALL)
    private List<ComandaProdus> produseComandate;

    // getters și setters
}
