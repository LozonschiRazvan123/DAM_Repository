package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Produs {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long idProdus;
    @NonNull
    private String nume;
    @NonNull
    private String categorie;
    @NonNull
    private Double pretVanzare;
    @NonNull
    private Double pretAchizitie;
    @NonNull
    private Integer stoc;
}
