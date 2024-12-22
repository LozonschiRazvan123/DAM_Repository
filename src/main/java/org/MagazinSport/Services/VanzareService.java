package org.MagazinSport.Services;

import org.MagazinSport.DTO.VanzareDTO;
import org.MagazinSport.Model.Vanzare;
import org.MagazinSport.Repository.VanzareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VanzareService {

    @Autowired
    private VanzareRepository vanzareRepository;



    // Conversia DTO -> Model
    public void saveVanzare(VanzareDTO vanzareDTO) {
        Vanzare vanzare = new Vanzare();
        vanzare.setData(vanzareDTO.getData());
        vanzare.setTotal(vanzareDTO.getTotal());
        vanzareRepository.save(vanzare);
    }

    public Vanzare getVanzareById(Long id) {
        return vanzareRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vanzare with ID " + id + " not found"));
    }
    public Map<String, Object> getDailySales() {
        List<Vanzare> vanzari = vanzareRepository.findAll();
        Map<String, Double> dailySales = vanzari.stream()
                .collect(Collectors.groupingBy(
                        v -> new SimpleDateFormat("EEEE").format(v.getData()),
                        Collectors.summingDouble(Vanzare::getTotal)
                ));
        return formatResponse(dailySales);
    }

    public Map<String, Object> getMonthlySales() {
        List<Vanzare> vanzari = vanzareRepository.findAll();
        Map<String, Double> monthlySales = vanzari.stream()
                .collect(Collectors.groupingBy(
                        v -> new SimpleDateFormat("MMMM").format(v.getData()),
                        Collectors.summingDouble(Vanzare::getTotal)
                ));
        return formatResponse(monthlySales);
    }

    public Map<String, Object> getYearlySales() {
        List<Vanzare> vanzari = vanzareRepository.findAll();
        Map<String, Double> yearlySales = vanzari.stream()
                .collect(Collectors.groupingBy(
                        v -> new SimpleDateFormat("yyyy").format(v.getData()),
                        Collectors.summingDouble(Vanzare::getTotal)
                ));
        return formatResponse(yearlySales);
    }

    private Map<String, Object> formatResponse(Map<String, Double> salesData) {
        Map<String, Object> response = new HashMap<>();
        response.put("labels", new ArrayList<>(salesData.keySet()));
        response.put("data", new ArrayList<>(salesData.values()));
        return response;
    }
}
