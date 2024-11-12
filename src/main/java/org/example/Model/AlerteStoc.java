package org.example.Model;

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
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @NotNull(message = "Alert ID is required!")
    private Long idAlerteStoc;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    @NotNull(message = "Associated product is required!")
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
        return (this.dataAlerta != null && this.dataAlerta.after(new Date())) == this.activ;
    }

    @Override
    public String toString() {
        return "AlerteStoc [idAlerteStoc=" + idAlerteStoc + ", produs=" + produs +
                ", activ=" + activ + ", dataAlerta=" + dataAlerta + "]";
    }
}
