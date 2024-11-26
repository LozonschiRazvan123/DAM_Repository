package org.example.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.TemporalType.DATE;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class ComandaAprovizionare implements Serializable {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @NotNull(message = "Order ID is required!")
    private Long idComandaAprovizionare;

    @Temporal(DATE)
    @NonNull
    @NotNull(message = "Order date is required!")
    @PastOrPresent(message = "Order date must be in the past or present!")
    private Date dataComanda;

    @Temporal(DATE)
    @NonNull
    @NotNull(message = "Estimated delivery date is required!")
    @FutureOrPresent(message = "Estimated delivery date must be in the present or future!")
    private Date dataLivrareEstimata;

    @NonNull
    @NotEmpty(message = "Status is required!")
    private String status;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NotNull(message = "Supplier is required!")
    private Furnizor furnizor;

    @OneToMany(mappedBy = "comandaAprovizionare", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ComandaProdus> produseComandate;

    @AssertTrue(message = "Estimated delivery date must be after the order date!")
    public Boolean isValidDeliveryDate() {
        return dataComanda != null && dataLivrareEstimata != null && !dataLivrareEstimata.before(dataComanda);
    }

    @Override
    public String toString() {
        return "ComandaAprovizionare [idComandaAprovizionare=" + idComandaAprovizionare + ", dataComanda=" + dataComanda +
                ", dataLivrareEstimata=" + dataLivrareEstimata + ", status=" + status + ", furnizor=" + furnizor +
                ", produseComandate=" + produseComandate + "]";
    }

    public ComandaAprovizionare(@NonNull Date dataComanda, @NonNull Date dataLivrareEstimata, @NonNull String status, Furnizor furnizor) {
        this.dataComanda = dataComanda;
        this.dataLivrareEstimata = dataLivrareEstimata;
        this.status = status;
        this.furnizor = furnizor;
        this.produseComandate = new ArrayList<>(); // Inițializează lista
    }
}
