package org.MagazinSport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Produs implements Serializable, Comparable<Produs> {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Product ID is required!")
    private Long idProdus;

    @NonNull
    @NotEmpty(message = "Product name is required!")
    private String nume;

    @NonNull
    @NotEmpty(message = "Product category is required!")
    private String categorie;

    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than zero!")
    private Double pretVanzare;

    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero!")
    private Double pretAchizitie;

    @NonNull
    @Min(value = 0, message = "Stock cannot be negative!")
    private Integer stoc;

    @ManyToOne
    @JoinColumn(name = "furnizor_id", referencedColumnName = "idFurnizor", nullable = false)
    @NotNull(message = "Supplier is required!")
    private Furnizor furnizor;

    // Constructorul pentru POST și PUT
    public Produs(String nume, String categorie, Double pretVanzare, Double pretAchizitie, Integer stoc, Furnizor furnizor) {
        this.nume = nume;
        this.categorie = categorie;
        this.pretVanzare = pretVanzare;
        this.pretAchizitie = pretAchizitie;
        this.stoc = stoc;
        this.furnizor = furnizor; // Asigură-te că furnizorul există deja sau este valid
    }

    @Override
    public String toString() {
        return "Produs [idProdus=" + idProdus + ", nume=" + nume + ", categorie=" + categorie +
                ", pretVanzare=" + pretVanzare + ", pretAchizitie=" + pretAchizitie + ", stoc=" + stoc + "]";
    }

    @Override
    public int compareTo(Produs other) {
        return this.pretVanzare.compareTo(other.pretVanzare);
    }

    public Double getProfit() {
        if (this.pretVanzare != null && this.pretAchizitie != null) {
            return this.pretVanzare - this.pretAchizitie;
        }
        return 0.0;
    }

    public Boolean isStockLow(int threshold) {
        return this.stoc != null && this.stoc < threshold;
    }
}
