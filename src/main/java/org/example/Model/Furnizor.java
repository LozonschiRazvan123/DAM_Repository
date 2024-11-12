package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Furnizor {
    @EqualsAndHashCode.Include
    @NonNull
    @Id
    private Long idFurnizor;

    @NonNull
    private String nume;
    @NonNull
    private String adresa;
    @NonNull
    private String telefon;
    @NonNull
    private String email;

    @OneToMany(mappedBy = "furnizor")
    private List<Produs> produse;
}
