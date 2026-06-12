package com.examen.mundial2026.controller;

import com.examen.mundial2026.model.Grupo;
import com.examen.mundial2026.model.Jugador;
import com.examen.mundial2026.service.MundialService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class MundialController {

    private final MundialService mundialService;

    public MundialController(MundialService mundialService) {
        this.mundialService = mundialService;
    }

    // ── Páginas HTML ──────────────────────────────────────────

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/seleccion/{pais}")
    public String seleccion(@PathVariable String pais, Model model) throws IOException {
        model.addAttribute("pais", pais);
        model.addAttribute("jugadores", mundialService.getJugadoresPorPais(pais));
        return "seleccion";
    }

    @GetMapping("/simulacion")
    public String simulacion(Model model) throws IOException {
        Map<String, Object> mundial = mundialService.simularMundial();
        model.addAttribute("octavos",      mundial.get("octavos"));
        model.addAttribute("cuartos",      mundial.get("cuartos"));
        model.addAttribute("semifinales",  mundial.get("semifinales"));
        model.addAttribute("finalPartido", mundial.get("final"));
        model.addAttribute("campeon",      mundial.get("campeon"));
        return "simulacion";
    }

    // ── API JSON ──────────────────────────────────────────────

    @GetMapping("/api/jugadores")
    @ResponseBody
    public ResponseEntity<List<Jugador>> getJugadores() throws IOException {
        return ResponseEntity.ok(mundialService.getJugadores());
    }

    @GetMapping("/api/grupos")
    @ResponseBody
    public ResponseEntity<List<Grupo>> getGrupos() throws IOException {
        return ResponseEntity.ok(mundialService.getGrupos());
    }
}
