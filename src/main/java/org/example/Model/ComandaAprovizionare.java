package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ComandaAprovizionare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComandaAprovizionare;

    private Date dataComanda;
    private Date dataLivrareEstimata;
    private String status;

    @ManyToOne
    private Furnizor furnizor;

    @OneToMany(mappedBy = "comandaAprovizionare", cascade = CascadeType.ALL)
    private List<ComandaProdus> produseComandate;

    public Long getIdComandaAprovizionare() {
        return idComandaAprovizionare;
    }

    public void setIdComandaAprovizionare(Long idComandaAprovizionare) {
        this.idComandaAprovizionare = idComandaAprovizionare;
    }

    public Date getDataComanda() {
        return dataComanda;
    }

    public void setDataComanda(Date dataComanda) {
        this.dataComanda = dataComanda;
    }

    public Date getDataLivrareEstimata() {
        return dataLivrareEstimata;
    }

    public void setDataLivrareEstimata(Date dataLivrareEstimata) {
        this.dataLivrareEstimata = dataLivrareEstimata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    public List<ComandaProdus> getProduseComandate() {
        return produseComandate;
    }

    public void setProduseComandate(List<ComandaProdus> produseComandate) {
        this.produseComandate = produseComandate;
    }
// getters și setters
}
