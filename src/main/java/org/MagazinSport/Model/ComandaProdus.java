package org.MagazinSport.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter @Setter
public class ComandaProdus implements Serializable, Comparable<ComandaProdus> {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @NotNull(message = "Product order ID is required!")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull(message = "The associated supply order is required!")
    @JsonIgnore
    private ComandaAprovizionare comandaAprovizionare;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NotNull(message = "The associated product is required!")
    private Produs produs;

    @NonNull
    @Min(value = 1, message = "Quantity must be at least 1!")
    private Integer cantitate;

    @NonNull
    @Min(value = 0, message = "Unit price must be non-negative!")
    private Double pretUnitate;

    @Override
    public int compareTo(ComandaProdus other) {
        return this.id.compareTo(other.getId());
    }


    @Override
    public String toString() {
        return "ComandaProdus [id=" + id + ", comandaAprovizionare=" + comandaAprovizionare +
                ", produs=" + produs + ", cantitate=" + cantitate + ", pretUnitate=" + pretUnitate + "]";
    }

    public ComandaProdus(ComandaAprovizionare comandaAprovizionare, Produs produs, @NonNull Integer cantitate, @NonNull Double pretUnitate) {
        this.comandaAprovizionare = comandaAprovizionare;
        this.produs = produs;
        this.cantitate = cantitate;
        this.pretUnitate = pretUnitate;
    }
}
