package org.MagazinSport.DTO;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Model.Produs;

import java.util.Date;

@Data
public class ComandaProdusDTO {
    public Long getFurnizorId() {
        return furnizorId;
    }

    public void setFurnizorId(Long furnizorId) {
        this.furnizorId = furnizorId;
    }

    private Long furnizorId;
    @NotNull(message = "ComandaAprovizionare ID is required!")
    private Long comandaAprovizionareId;

    @NotNull(message = "Produs ID is required!")
    private Long produsId;

    @NotNull(message = "Quantity is required!")
    private Integer cantitate;

    @NotNull(message = "Unit price is required!")
    private Double pretUnitate;

    @NotNull(message = "Order date is required!")
    private Date dataComanda;

    private Produs produs;

    private Furnizor furnizor;

    public @NotNull(message = "ComandaAprovizionare ID is required!") Long getComandaAprovizionareId() {
        return comandaAprovizionareId;
    }

    public void setComandaAprovizionareId(@NotNull(message = "ComandaAprovizionare ID is required!") Long comandaAprovizionareId) {
        this.comandaAprovizionareId = comandaAprovizionareId;
    }

    public @NotNull(message = "Produs ID is required!") Long getProdusId() {
        return produsId;
    }

    public void setProdusId(@NotNull(message = "Produs ID is required!") Long produsId) {
        this.produsId = produsId;
    }

    public @NotNull(message = "Quantity is required!") Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(@NotNull(message = "Quantity is required!") Integer cantitate) {
        this.cantitate = cantitate;
    }

    public @NotNull(message = "Unit price is required!") Double getPretUnitate() {
        return pretUnitate;
    }

    public void setPretUnitate(@NotNull(message = "Unit price is required!") Double pretUnitate) {
        this.pretUnitate = pretUnitate;
    }

    public @NotNull(message = "Order date is required!") Date getDataComanda() {
        return dataComanda;
    }

    public void setDataComanda(@NotNull(message = "Order date is required!") Date dataComanda) {
        this.dataComanda = dataComanda;
    }

    public void setId(@NonNull @Min(1) @NotNull(message = "Product order ID is required!") Long id) {
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }
}