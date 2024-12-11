package org.MagazinSport.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class AlerteStocDTO {
    private Long id;

    @NotNull(message = "Product ID is required!")
    private Long produsId;

    @NotNull(message = "Active status is required!")
    private Boolean activ;

    @NotNull(message = "Alert date is required!")
    @FutureOrPresent(message = "Alert date must be in the present or future!")
    private Date dataAlerta;
}
