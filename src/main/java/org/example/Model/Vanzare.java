package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Vanzare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date data;
    private double total;

    @OneToMany(mappedBy = "vanzare", cascade = CascadeType.ALL)
    private List<VanzareProdus> produseVandute;


}