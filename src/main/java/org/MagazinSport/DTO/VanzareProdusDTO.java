package org.MagazinSport.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VanzareProdusDTO {

    @NotNull(message = "Product Sale ID is required!")
    private Long id;

    @NotNull(message = "Sale ID is required!")
    private Long vanzareId;

    @NotNull(message = "Product ID is required!")
    private Long produsId;

    @NotNull(message = "Quantity is required!")
    @Min(value = 1, message = "Quantity must be at least 1!")
    private Integer cantitate;

    @NotNull(message = "Unit price is required!")
    @Min(value = 0, message = "Unit price must be at least 0!")
    private Double pretUnitate;

    public VanzareProdusDTO() {
    }

    public VanzareProdusDTO(Long id, Long vanzareId, Long produsId, Integer cantitate, Double pretUnitate) {
        this.id = id;
        this.vanzareId = vanzareId;
        this.produsId = produsId;
        this.cantitate = cantitate;
        this.pretUnitate = pretUnitate;
    }

    public Double getTotal() {
        return this.cantitate != null && this.pretUnitate != null ? this.cantitate * this.pretUnitate : 0.0;
    }
}
