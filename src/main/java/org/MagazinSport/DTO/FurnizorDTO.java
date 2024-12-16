package org.MagazinSport.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FurnizorDTO {

    private Long idFurnizor;

    @NotNull(message = "Supplier name is required!")
    @NotEmpty(message = "Supplier name cannot be empty!")
    private String nume;

    @NotNull(message = "Supplier address is required!")
    @NotEmpty(message = "Supplier address cannot be empty!")
    private String adresa;

    @NotNull(message = "Supplier phone is required!")
    @NotEmpty(message = "Supplier phone cannot be empty!")
    @Pattern(regexp = "^(\\+40|0)[0-9]{9}$", message = "The format of the number is not Romanian-friendly!")
    private String telefon;

    @NotNull(message = "Supplier email is required!")
    @NotEmpty(message = "Supplier email cannot be empty!")
    @Email(message = "Invalid email format")
    private String email;
}
