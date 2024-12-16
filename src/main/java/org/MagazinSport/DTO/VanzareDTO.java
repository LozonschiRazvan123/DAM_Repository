package org.MagazinSport.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VanzareDTO {

    @NotNull(message = "Sale ID is required!")
    private Long id;

    @NotNull(message = "Sale date is required!")
    private Date data;

    @NotNull(message = "Total sale amount is required!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total sale amount must be greater than zero!")
    private Double total;

    @NotNull(message = "Sold products list is required!")
    private List<VanzareProdusDTO> produseVandute;

    public VanzareDTO() {
    }

    public VanzareDTO(Long id, Date data, Double total, List<VanzareProdusDTO> produseVandute) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.produseVandute = produseVandute;
    }
}
