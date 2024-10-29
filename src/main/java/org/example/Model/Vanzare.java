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

    public Vanzare(Long id, Date data, double total, List<VanzareProdus> produseVandute) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.produseVandute = produseVandute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<VanzareProdus> getProduseVandute() {
        return produseVandute;
    }

    public void setProduseVandute(List<VanzareProdus> produseVandute) {
        this.produseVandute = produseVandute;
    }
}