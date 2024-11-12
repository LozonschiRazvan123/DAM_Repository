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
@Getter
@Setter
public class Vanzare {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long id;
    @Temporal(DATE)
    @NonNull
    private Date data;
    @NonNull
    private Double total;

    @OneToMany(mappedBy = "vanzare", cascade = CascadeType.ALL)
    private List<VanzareProdus> produseVandute;
}