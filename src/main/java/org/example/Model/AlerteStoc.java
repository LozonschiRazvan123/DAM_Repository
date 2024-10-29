package org.example.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class AlerteStoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerteStoc;

    public Long getIdAlerteStoc() {
        return idAlerteStoc;
    }

    public void setIdAlerteStoc(Long IdAlerteStoc) {
        this.idAlerteStoc = IdAlerteStoc;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public Date getDataAlerta() {
        return dataAlerta;
    }

    public void setDataAlerta(Date dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    @OneToOne
    private Produs produs;

    private boolean activ;
    private Date dataAlerta;

    public AlerteStoc(Long id, Produs produs, boolean activ, Date dataAlerta) {
        this.idAlerteStoc = idAlerteStoc;
        this.produs = produs;
        this.activ = activ;
        this.dataAlerta = dataAlerta;
    }
// getters și setters
}
