package org.MagazinSport.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class StocDTO {

    @NotNull(message = "Stock ID is required!")
    private Long idStoc;

    @NotNull(message = "Product ID is required!")
    private Long produsId;

    @NotNull(message = "Product name is required!")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters!")
    private String numeProdus;

    @NotNull(message = "Quantity is required!")
    @Min(value = 0, message = "Quantity must be zero or greater!")
    private Integer cantitate;

    @NotNull(message = "Minimum level is required!")
    @Min(value = 0, message = "Minimum level must be zero or greater!")
    private Integer nivelMinim;

    @NotNull(message = "Last modification date is required!")
    private Date dataUltimeiModificari;

    // Constructori
    public StocDTO() {
    }

    public StocDTO(Long idStoc, Long produsId, String numeProdus, Integer cantitate, Integer nivelMinim, Date dataUltimeiModificari) {
        this.idStoc = idStoc;
        this.produsId = produsId;
        this.numeProdus = numeProdus;
        this.cantitate = cantitate;
        this.nivelMinim = nivelMinim;
        this.dataUltimeiModificari = dataUltimeiModificari;
    }
}