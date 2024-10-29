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

    public Stoc(Long idStoc, Produs produs, int cantitate, int nivelMinim, Date dataUltimeiModificari) {
        this.idStoc = idStoc;
        this.produs = produs;
        this.cantitate = cantitate;
        this.nivelMinim = nivelMinim;
        this.dataUltimeiModificari = dataUltimeiModificari;
    }
    public Stoc()
    {

    }

    public Long getIdStoc() {
        return idStoc;
    }

    public void setIdStoc(Long idStoc) {
        this.idStoc = idStoc;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getNivelMinim() {
        return nivelMinim;
    }

    public void setNivelMinim(int nivelMinim) {
        this.nivelMinim = nivelMinim;
    }

    public Date getDataUltimeiModificari() {
        return dataUltimeiModificari;
    }

    public void setDataUltimeiModificari(Date dataUltimeiModificari) {
        this.dataUltimeiModificari = dataUltimeiModificari;
    }
    // getters și setters
}
