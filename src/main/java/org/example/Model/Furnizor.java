package org.example.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Furnizor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFurnizor;

    private String nume;
    private String adresa;
    private String telefon;
    private String email;

    @OneToMany(mappedBy = "furnizor")
    private List<Produs> produse;

    public Furnizor(Long idFurnizor, String nume, String adresa, String telefon, String email, List<Produs> produse) {
        this.idFurnizor = idFurnizor;
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.produse = produse;
    }
public Furnizor()
{

}
    public Long getIdFurnizor() {
        return idFurnizor;
    }

    public void setIdFurnizor(Long idFurnizor) {
        this.idFurnizor = idFurnizor;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }
    // getters și setters
}
