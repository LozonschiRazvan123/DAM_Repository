package org.MagazinSport.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Furnizor implements Serializable {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Supplier ID is required!")
    private Long idFurnizor;

    @NonNull
    @NotEmpty(message = "Supplier name is required!")
    private String nume;

    @NonNull
    @NotEmpty(message = "Supplier address is required!")
    private String adresa;

    @NonNull
    @NotEmpty(message = "Supplier phone is required!")
    @Pattern(regexp = "^(\\+40|0)[0-9]{9}$", message = "The format of the number is not romanian friendly!")
    private String telefon;

    @NonNull
    @NotEmpty(message = "Supplier email is required!")
    @Email(message = "Invalid email format")
    private String email;

    @OneToMany(mappedBy = "furnizor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Produs> produse;

    public Furnizor(String nume, String adresa, String telefon, String email) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
    }
    @Override
    public String toString() {
        return "Furnizor [idFurnizor=" + idFurnizor + ", nume=" + nume + ", adresa=" + adresa +
                ", telefon=" + telefon + ", email=" + email + ", produse=" + produse + "]";
    }
}
