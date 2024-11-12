package org.example.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static jakarta.persistence.TemporalType.DATE;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Stoc {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long idStoc;

    @OneToOne
    private Produs produs;
    @NonNull
    private Integer cantitate;
    @NonNull
    private Integer nivelMinim;
    @Temporal(DATE)
    @NonNull
    private Date dataUltimeiModificari;
}
