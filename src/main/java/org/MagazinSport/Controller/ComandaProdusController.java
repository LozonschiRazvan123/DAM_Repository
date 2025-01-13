package org.MagazinSport.Controller;

import org.MagazinSport.DTO.ComandaProdusDTO;
import org.MagazinSport.Model.ComandaAprovizionare;
import org.MagazinSport.Model.ComandaProdus;
import org.MagazinSport.Model.Furnizor;
import org.MagazinSport.Model.Produs;
import org.MagazinSport.Services.ComandaAprovizionareService;
import org.MagazinSport.Services.ComandaProdusService;
import org.MagazinSport.Services.FurnizorService;
import org.MagazinSport.Services.ProdusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comenzi")
public class ComandaProdusController {

    private final ProdusService produsService;
    private final ComandaProdusService comandaService;
    private final FurnizorService furnizorService;
    private final ComandaAprovizionareService comandaAprovizionareService;

    public ComandaProdusController(ProdusService produsService, ComandaProdusService comandaService, FurnizorService furnizorService, ComandaAprovizionareService comandaAprovizionareService) {
        this.produsService = produsService;
        this.comandaService = comandaService;
        this.furnizorService = furnizorService;
        this.comandaAprovizionareService = comandaAprovizionareService;
    }

    @GetMapping
    public String viewComenzi(Model model) {
        List<Produs> produse = produsService.getAllProduse();
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        List<ComandaProdus> comandaProduse = comandaService.getAllComandaProduse();
        List<ComandaProdusDTO> comenziDTO = comandaProduse.stream().map(comandaProdus -> {
            ComandaProdusDTO dto = new ComandaProdusDTO();

            if (comandaProdus.getComandaAprovizionare() != null) {
                dto.setComandaAprovizionareId(comandaProdus.getComandaAprovizionare().getIdComandaAprovizionare());
                dto.setDataComanda(comandaProdus.getComandaAprovizionare().getDataComanda());
            } else {
                dto.setComandaAprovizionareId(null);
                dto.setDataComanda(null);
            }

            dto.setProdus(comandaProdus.getProdus());
            dto.setCantitate(comandaProdus.getCantitate());
            dto.setPretUnitate(comandaProdus.getPretUnitate());

            if (comandaProdus.getProdus().getFurnizor() != null) {
                dto.setFurnizor(comandaProdus.getProdus().getFurnizor());
            } else {
                dto.setFurnizor(null);
            }

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("comenzi", comenziDTO);
        model.addAttribute("produse", produse);
        model.addAttribute("furnizori", furnizori);
        return "comenzi";
    }

    @PostMapping
    public String addComanda(@RequestParam Map<String, String> params, @RequestParam Long furnizorId, Model model) {
        System.out.println("Parametrii primiți în metodă:");
        params.forEach((key, value) -> System.out.println("Param: " + key + ", Value: " + value));

        ComandaAprovizionare comanda = new ComandaAprovizionare();
        comanda.setDataComanda(new Date());
        comanda.setStatus("In proces");

        comanda.setFurnizor(furnizorService.getFurnizorById(furnizorId).orElseThrow(() ->
                new IllegalArgumentException("Furnizorul nu a fost gasit!")));

        List<ComandaProdus> comandaProduse = new ArrayList<>();

        for (String key : params.keySet()) {
            if (key.contains("produseDTO")) {
                String[] keyParts = key.split("\\[|\\]");
                if (keyParts.length > 1) {
                    int produsIndex = Integer.parseInt(keyParts[1]);

                    if (key.contains(".produsId")) {
                        String idProdusKey = "produseDTO[" + produsIndex + "].produsId";
                        String cantitateKey = "produseDTO[" + produsIndex + "].cantitate";

                        if (params.containsKey(idProdusKey) && params.containsKey(cantitateKey)) {
                            Long produsId = Long.parseLong(params.get(idProdusKey));
                            Integer cantitate = Integer.parseInt(params.get(cantitateKey));

                            if (cantitate > 0) {
                                Produs produs = produsService.getProdusById(produsId).orElseThrow(() ->
                                        new IllegalArgumentException("Produsul cu ID-ul " + produsId + " nu a fost găsit!"));

                                if (produs.getStoc() < cantitate) {
                                    throw new IllegalArgumentException("Stoc insuficient pentru produsul: " + produs.getNume());
                                }

                                produs.setStoc(produs.getStoc() - cantitate);
                                produsService.updateProdus(produs.getIdProdus(), produs);

                                ComandaProdus comandaProdus = new ComandaProdus(produs, cantitate, produs.getPretVanzare(), new Date());
                                comandaProdus.setComandaAprovizionare(comanda);
                                comandaProduse.add(comandaProdus);

                                System.out.println("Adăugat: Produs ID: " + produsId +
                                        ", Cantitate: " + cantitate +
                                        ", Preț unitar: " + produs.getPretVanzare() +
                                        ", Stoc rămas: " + produs.getStoc());
                            }
                        }
                    }
                }
            }
        }

        if (comandaProduse.isEmpty()) {
            throw new IllegalArgumentException("Nu s-au adaugat produse la comanda!");
        }

        comanda.setProduseComandate(comandaProduse);
        comandaAprovizionareService.saveComandaAprovizionare(comanda);

        return "redirect:/comenzi";
    }

    @PostMapping("/save")
    public String saveComanda(
            @RequestParam("furnizor.idFurnizor") Long furnizorId,
            @RequestParam Map<String, String> params
    ) {
        Furnizor furnizor = furnizorService.getFurnizorById(furnizorId)
                .orElseThrow(() -> new IllegalArgumentException("Furnizorul nu a fost găsit!"));

        ComandaAprovizionare comanda = new ComandaAprovizionare();
        comanda.setFurnizor(furnizor);
        comanda.setDataComanda(new Date());
        comanda.setStatus("În proces");

        List<ComandaProdus> comandaProduse = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String produsKey = "produseDTO[" + i + "].produsId";
            String cantitateKey = "produseDTO[" + i + "].cantitate";

            if (params.containsKey(produsKey) && params.containsKey(cantitateKey)) {
                Long produsId = Long.parseLong(params.get(produsKey));
                int cantitate = Integer.parseInt(params.get(cantitateKey));

                if (cantitate >= 0) {
                    Produs produs = produsService.getProdusById(produsId)
                            .orElseThrow(() -> new IllegalArgumentException("Produsul nu a fost găsit!"));

                    if (produs.getStoc() < cantitate) {
                        throw new IllegalArgumentException("Stoc insuficient pentru produsul: " + produs.getNume());
                    }

                    produs.setStoc(produs.getStoc() - cantitate);
                    produsService.updateProdus(produsId, produs);

                    ComandaProdus comandaProdus = new ComandaProdus();
                    comandaProdus.setProdus(produs);
                    comandaProdus.setCantitate(cantitate);
                    comandaProdus.setPretUnitate(produs.getPretVanzare());
                    comandaProdus.setDataComanda(new Date());
                    comandaProdus.setComandaAprovizionare(comanda);

                    comandaProduse.add(comandaProdus);
                }
            }
        }

        if (comandaProduse.isEmpty()) {
            throw new IllegalArgumentException("Nu s-au selectat produse pentru comandă!");
        }

        comanda.setProduseComandate(comandaProduse);
        comandaAprovizionareService.saveComandaAprovizionare(comanda);

        return "redirect:/comenzi";
    }

    @GetMapping("/edit/{id}")
    public String editComanda(@PathVariable("id") Long id, Model model) {
        ComandaAprovizionare comanda = comandaAprovizionareService.findById(id);
        if (comanda == null) {
            throw new IllegalArgumentException("Comanda cu ID-ul " + id + " nu a fost găsită!");
        }

        List<Furnizor> furnizori = furnizorService.getAllFurnizori();

        model.addAttribute("comanda", comanda);
        model.addAttribute("furnizori", furnizori);

        return "updateComanda";
    }
    @PostMapping("/update")
    public String updateComanda(
            @RequestParam("idComandaAprovizionare") Long idComanda,
            @RequestParam("furnizor.idFurnizor") Long furnizorId,
            @RequestParam Map<String, String> params) {

        ComandaAprovizionare comanda = comandaAprovizionareService.findById(idComanda);
        if (comanda == null) {
            throw new IllegalArgumentException("Comanda cu ID-ul " + idComanda + " nu a fost găsită!");
        }

        Furnizor furnizor = furnizorService.getFurnizorById(furnizorId)
                .orElseThrow(() -> new IllegalArgumentException("Furnizorul nu a fost găsit!"));
        comanda.setFurnizor(furnizor);

        comanda.getProduseComandate().clear();

        for (int i = 0; i < 5; i++) {
            String produsKey = "produseDTO[" + i + "].produsId";
            String cantitateKey = "produseDTO[" + i + "].cantitate";

            if (params.containsKey(produsKey) && params.containsKey(cantitateKey)) {
                Long produsId = Long.parseLong(params.get(produsKey));
                int cantitate = Integer.parseInt(params.get(cantitateKey));

                Produs produs = produsService.getProdusById(produsId)
                        .orElseThrow(() -> new IllegalArgumentException("Produsul nu a fost găsit!"));

                ComandaProdus comandaProdus = new ComandaProdus();
                comandaProdus.setProdus(produs);
                comandaProdus.setCantitate(cantitate);
                comandaProdus.setPretUnitate(produs.getPretVanzare());
                comandaProdus.setComandaAprovizionare(comanda);

                comanda.getProduseComandate().add(comandaProdus);
            }
        }

        // Salvează modificările
        comandaAprovizionareService.saveComandaAprovizionare(comanda);

        return "redirect:/comenzi";
    }


    @GetMapping("/delete/{id}")
    public String deleteComanda(@PathVariable("id") Long id) {
        comandaAprovizionareService.deleteComandaAprovizionare(id);
        return "redirect:/comenzi";
    }


//    {
//        "comandaAprovizionareId": 1,
//            "produsId": 2,
//            "cantitate": 15,
//            "pretUnitate": 50.5,
//            "dataComanda": "2024-12-12"
//    }

}
