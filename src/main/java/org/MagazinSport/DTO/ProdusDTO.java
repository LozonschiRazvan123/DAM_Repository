package org.MagazinSport.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdusDTO {

    private Long idProdus;

    @NotEmpty(message = "Product name is required!")
    private String nume;

    @NotEmpty(message = "Product category is required!")
    private String categorie;

    @DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than zero!")
    private Double pretVanzare;

    @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero!")
    private Double pretAchizitie;

    @Min(value = 0, message = "Stock cannot be negative!")
    private Integer stoc;

    @NotNull(message = "Supplier ID is required!")
    private Long furnizorId;
}
