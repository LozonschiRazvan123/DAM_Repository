package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AlerteStoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Produs produs;

    private boolean activ;
    private Date dataAlerta;

    // getters și setters
}
