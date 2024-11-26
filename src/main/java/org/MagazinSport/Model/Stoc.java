package org.MagazinSport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.TemporalType.DATE;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Stoc implements Serializable {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Stock ID is required!")
    private Long idStoc;

    @OneToOne
    @JoinColumn(name = "produs_id", referencedColumnName = "idProdus", nullable = false)
    @NotNull(message = "Product is required!")
    private Produs produs;


    @NonNull
    @Min(value = 0, message = "Quantity must be zero or greater!")
    private Integer cantitate;

    @NonNull
    @Min(value = 0, message = "Minimum level must be zero or greater!")
    private Integer nivelMinim;

    @Temporal(DATE)
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataUltimeiModificari;

    public Boolean isBelowMinimumLevel() {
        return this.cantitate != null && this.cantitate < this.nivelMinim;
    }

    public void updateStock(int newQuantity) {
        this.cantitate = newQuantity;
        this.dataUltimeiModificari = new Date();
    }
    public void incrementStock(int quantity) {
        if (quantity > 0) {
            this.cantitate += quantity;
            this.dataUltimeiModificari = new Date();
        }
    }

    public void decrementStock(int quantity) {
        if (quantity > 0 && this.cantitate - quantity >= 0) {
            this.cantitate -= quantity;
            this.dataUltimeiModificari = new Date();
        }
    }
    public Stoc(Produs produs, Integer cantitate, Integer nivelMinim, Date dataUltimeiModificari) {
        this.produs = produs;
        this.cantitate = cantitate;
        this.nivelMinim = nivelMinim;
        this.dataUltimeiModificari = dataUltimeiModificari;
    }
}
