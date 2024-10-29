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


}

