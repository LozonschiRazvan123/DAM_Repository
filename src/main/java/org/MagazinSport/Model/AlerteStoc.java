package org.MagazinSport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class AlerteStoc implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @NotNull(message = "Alert ID is required!")
    private Long idAlerteStoc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produs_id")
    private Produs produs;


    @NonNull
    @NotNull(message = "Active status is required!")
    private Boolean activ;

    @NonNull
    @NotNull(message = "Alert date is required!")
    @FutureOrPresent(message = "Alert date must be in the present or future!")
    @Temporal(TemporalType.DATE)
    private Date dataAlerta;

    @AssertTrue(message = "Alert must be active if the alert date is in the future!")
    public Boolean isValid() {
        return (this.dataAlerta != null) &&
                (this.dataAlerta.after(new Date()) == Boolean.TRUE.equals(this.activ));
    }

    @Override
    public String toString() {
        return "AlerteStoc [idAlerteStoc=" + idAlerteStoc +
                ", produs=" + (produs != null ? produs.getNume() : "null") +
                ", activ=" + activ +
                ", dataAlerta=" + dataAlerta + "]";
    }

    public AlerteStoc(Produs produs, @NonNull Boolean activ, @NonNull Date dataAlerta) {
        this.produs = produs;
        this.activ = activ;
        this.dataAlerta = dataAlerta;
    }

}
