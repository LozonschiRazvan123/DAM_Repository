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
public class VanzareProdus {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long id;

    @ManyToOne
    private Vanzare vanzare;

    @ManyToOne
    private Produs produs;

    @NonNull
    private Integer cantitate;
    @NonNull
    private Double pretUnitate;
}

