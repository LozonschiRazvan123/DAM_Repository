package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.TemporalType.DATE;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class ComandaAprovizionare {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long idComandaAprovizionare;

    @Temporal(DATE)
    @NonNull
    private Date dataComanda;
    @Temporal(DATE)
    @NonNull
    private Date dataLivrareEstimata;
    @NonNull
    private String status;

    @ManyToOne
    private Furnizor furnizor;

    @OneToMany(mappedBy = "comandaAprovizionare", cascade = CascadeType.ALL)
    private List<ComandaProdus> produseComandate;

}
