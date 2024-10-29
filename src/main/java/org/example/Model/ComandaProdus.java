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

    // getters și setters
}
