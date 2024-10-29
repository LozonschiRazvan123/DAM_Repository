package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Stoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStoc;

    @OneToOne
    private Produs produs;

    private int cantitate;
    private int nivelMinim;
    private Date dataUltimeiModificari;

    // getters și setters
}
