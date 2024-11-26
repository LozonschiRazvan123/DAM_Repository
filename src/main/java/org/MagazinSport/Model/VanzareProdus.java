package org.MagazinSport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class VanzareProdus implements Serializable, Comparable<VanzareProdus> {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID is required!")
    private Long id;

    @ManyToOne
    @NotNull(message = "Sale is required!")
    private Vanzare vanzare;

    @ManyToOne
    @NotNull(message = "Product is required!")
    private Produs produs;

    @NonNull
    @Min(value = 1, message = "Quantity must be at least 1!")
    private Integer cantitate;

    @NonNull
    @Min(value = 0, message = "Unit price must be at least 0!")
    private Double pretUnitate;

    @Override
    public String toString() {
        return "VanzareProdus [id=" + id + ", vanzare=" + vanzare + ", produs=" + produs +
                ", cantitate=" + cantitate + ", pretUnitate=" + pretUnitate + "]";
    }

    @Override
    public int compareTo(VanzareProdus other) {

        return this.pretUnitate.compareTo(other.pretUnitate);
    }

    public Double getTotal() {

        if (this.pretUnitate != null && this.cantitate != null) {
            return this.pretUnitate * this.cantitate;
        }
        return 0.0;
    }

    public Boolean isValidQuantity() {

        return this.cantitate != null && this.cantitate > 0;
    }

    public VanzareProdus(Vanzare vanzare, Produs produs, @NonNull Integer cantitate, @NonNull Double pretUnitate) {
        this.vanzare = vanzare;
        this.produs = produs;
        this.cantitate = cantitate;
        this.pretUnitate = pretUnitate;
    }
}
