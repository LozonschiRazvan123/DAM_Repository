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

        private String label; // zi/luna/an
//        private Double total; // total vânzări

        public VanzareDTO(String label, Double total) {
            this.label = label;
            this.total = total;
        }

        // Getters și Setters
        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    public VanzareDTO(Long id, Date data, Double total, List<VanzareProdusDTO> produseVandute) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.produseVandute = produseVandute;
    }

}

