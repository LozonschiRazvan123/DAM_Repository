package org.MagazinSport.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ComandaAprovizionareDTO {
    private Long id;

    @NotNull(message = "Order date is required!")
    private Date dataComanda;

    @NotNull(message = "Estimated delivery date is required!")
    @FutureOrPresent(message = "Estimated delivery date must be in the present or future!")
    private Date dataLivrareEstimata;

    @NotEmpty(message = "Status is required!")
    private String status;

    @NotNull(message = "Supplier ID is required!")
    private Long furnizorId;
}
