// Controller pentru gestionarea comenzilor
package org.MagazinSport.Controller;

import org.MagazinSport.DTO.ComandaProdusDTO;
import org.MagazinSport.Model.*;
import org.MagazinSport.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comenzi")
public class ComandaProdusController {

    private final ProdusService produsService;
    private final ComandaProdusService comandaService;
    private final FurnizorService furnizorService;
    private final ComandaAprovizionareService comandaAprovizionareService;
    private final StocService stocService;
    private final AlerteStocService alerteStoc;

    public ComandaProdusController(ProdusService produsService, ComandaProdusService comandaService,
                                   FurnizorService furnizorService, ComandaAprovizionareService comandaAprovizionareService,
                                   StocService stocService, AlerteStocService alerteStoc) {
        this.produsService = produsService;
        this.comandaService = comandaService;
        this.furnizorService = furnizorService;
        this.comandaAprovizionareService = comandaAprovizionareService;
        this.stocService = stocService;
        this.alerteStoc = alerteStoc;
    }

    // Afișarea paginii de comenzi
    @GetMapping
    public String viewComenzi(Model model) {
        List<Produs> produse = produsService.getAllProduse();
        List<Furnizor> furnizori = furnizorService.getAllFurnizori();
        List<ComandaProdus> comandaProduse = comandaService.getAllComandaProduse();

        // Mapare în DTO pentru afișare
        List<ComandaProdusDTO> comenziDTO = comandaProduse.stream().map(comandaProdus -> {
            ComandaProdusDTO dto = new ComandaProdusDTO();

            if (comandaProdus.getComandaAprovizionare() != null) {
                dto.setComandaAprovizionareId(comandaProdus.getComandaAprovizionare().getIdComandaAprovizionare());
                dto.setDataComanda(comandaProdus.getComandaAprovizionare().getDataComanda());
            }

            dto.setProdus(comandaProdus.getProdus());
            dto.setCantitate(comandaProdus.getCantitate());
            dto.setPretUnitate(comandaProdus.getPretUnitate());

            if (comandaProdus.getProdus().getFurnizor() != null) {
                dto.setFurnizor(comandaProdus.getProdus().getFurnizor());
            }

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("comenzi", comenziDTO);
        model.addAttribute("produse", produse);
        model.addAttribute("furnizori", furnizori);
        return "comenzi";
    }

    @PostMapping("/save")
    public String saveComanda(@RequestParam("furnizor.idFurnizor") Long furnizorId,
                              @RequestParam Map<String, String> params,
                              RedirectAttributes redirectAttributes) {
        try {
            ComandaAprovizionare comanda = new ComandaAprovizionare();
            comanda.setFurnizor(furnizorService.getFurnizorById(furnizorId).orElseThrow(() ->
                    new IllegalArgumentException("Furnizorul nu a fost găsit!")));
            comanda.setDataComanda(new Date());
            comanda.setStatus("În proces");

            List<ComandaProdus> produseComandate = parseProduse(params, comanda);
            if (produseComandate.isEmpty()) {
                throw new IllegalArgumentException("Comanda trebuie să conțină cel puțin un produs!");
            }

            comanda.setProduseComandate(produseComandate);
            comandaAprovizionareService.saveComandaAprovizionare(comanda);
            alerteStoc.generateAlerteForLowStock();

            redirectAttributes.addFlashAttribute("successMessage", "Comanda a fost salvată cu succes!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/comenzi";
    }

    // Actualizarea unei comenzi existente
    @PostMapping("/update")
    public String updateComanda(
            @RequestParam("idComandaAprovizionare") Long idComanda,
            @RequestParam("furnizor.idFurnizor") Long furnizorId,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Obține comanda existentă
            ComandaAprovizionare comanda = comandaAprovizionareService.findById(idComanda);
            if (comanda == null) {
                throw new IllegalArgumentException("Comanda cu ID-ul " + idComanda + " nu a fost găsită!");
            }

            // Actualizează furnizorul
            Furnizor furnizor = furnizorService.getFurnizorById(furnizorId)
                    .orElseThrow(() -> new IllegalArgumentException("Furnizorul nu a fost găsit!"));
            comanda.setFurnizor(furnizor);

            // Actualizează lista de produse comandate
            List<ComandaProdus> produseComandate = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                String produsKey = "produseDTO[" + i + "].produsId";
                String cantitateKey = "produseDTO[" + i + "].cantitate";

                if (params.containsKey(produsKey) && params.containsKey(cantitateKey)) {
                    Long produsId = Long.parseLong(params.get(produsKey));
                    int cantitate = Integer.parseInt(params.get(cantitateKey));

                    Produs produs = produsService.getProdusById(produsId)
                            .orElseThrow(() -> new IllegalArgumentException("Produsul nu a fost găsit!"));

                    if (produs.getStoc() < cantitate) {
                        throw new IllegalArgumentException("Stoc insuficient pentru produsul: " + produs.getNume());
                    }

                    ComandaProdus comandaProdus = new ComandaProdus();
                    comandaProdus.setProdus(produs);
                    comandaProdus.setCantitate(cantitate);
                    comandaProdus.setPretUnitate(produs.getPretVanzare());
                    comandaProdus.setComandaAprovizionare(comanda);

                    produseComandate.add(comandaProdus);
                }
            }

            if (produseComandate.isEmpty()) {
                throw new IllegalArgumentException("Comanda trebuie să conțină cel puțin un produs!");
            }

            // Golește lista existentă și adaugă produsele noi
            comanda.getProduseComandate().clear();
            comanda.getProduseComandate().addAll(produseComandate);

            // Salvează modificările
            comandaAprovizionareService.saveComandaAprovizionare(comanda);

            redirectAttributes.addFlashAttribute("successMessage", "Comanda a fost actualizată cu succes!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/comenzi";
    }


    // Metodă comună pentru procesarea produselor
    private List<ComandaProdus> parseProduse(Map<String, String> params, ComandaAprovizionare comanda) {
        List<ComandaProdus> produseComandate = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String produsKey = "produseDTO[" + i + "].produsId";
            String cantitateKey = "produseDTO[" + i + "].cantitate";

            if (params.containsKey(produsKey) && params.containsKey(cantitateKey)) {
                Long produsId = Long.parseLong(params.get(produsKey));
                int cantitate = Integer.parseInt(params.get(cantitateKey));

                Produs produs = produsService.getProdusById(produsId).orElseThrow(() ->
                        new IllegalArgumentException("Produsul nu a fost găsit!"));

                if (produs.getStoc() < cantitate) {
                    throw new IllegalArgumentException("Stoc insuficient pentru produsul: " + produs.getNume());
                }

                produs.setStoc(produs.getStoc() - cantitate);
                produsService.updateProdus(produs.getIdProdus(), produs);

                ComandaProdus comandaProdus = new ComandaProdus();
                comandaProdus.setProdus(produs);
                comandaProdus.setCantitate(cantitate);
                comandaProdus.setPretUnitate(produs.getPretVanzare());
                comandaProdus.setComandaAprovizionare(comanda);

                produseComandate.add(comandaProdus);
            }
        }
        return produseComandate;
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

    @GetMapping("/delete/{id}")
    public String deleteComanda(@PathVariable("id") Long id) {
        comandaAprovizionareService.deleteComandaAprovizionare(id);
        return "redirect:/comenzi";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/comenzi";
    }
}
