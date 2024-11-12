package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class AlerteStoc {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long idAlerteStoc;
    @OneToOne
    private Produs produs;

    @NonNull
    private boolean activ;
    @NonNull
    private Date dataAlerta;
}
