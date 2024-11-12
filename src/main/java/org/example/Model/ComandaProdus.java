package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class ComandaProdus {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long id;

    @ManyToOne
    private ComandaAprovizionare comandaAprovizionare;

    @ManyToOne
    private Produs produs;

    @NonNull
    private int cantitate;
    @NonNull
    private double pretUnitate;
}
