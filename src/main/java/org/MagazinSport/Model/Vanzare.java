package org.MagazinSport.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Vanzare implements Serializable {

    @EqualsAndHashCode.Include
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Sale ID is required!")
    private Long id;

    @Temporal(DATE)
    @NonNull
    @NotNull(message = "Sale date is required!")
    private Date data;

    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Total sale amount must be greater than zero!")
    private Double total;

    @OneToMany(mappedBy = "vanzare", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VanzareProdus> produseVandute;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "idUser")
    private User user;

    public Double calculateTotal() {
        if (this.produseVandute != null) {
            return this.produseVandute.stream()
                    .mapToDouble(vp -> vp.getCantitate() * vp.getPretUnitate())
                    .sum();
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Vanzare [id=" + id + ", data=" + data + ", total=" + total +
                ", produseVandute=" + produseVandute + "]";
    }

    public Vanzare(@NonNull Date data, @NonNull Double total) {
        this.data = data;
        this.total = total;
        this.produseVandute = new ArrayList<>(); // Inițializează lista
    }
}
