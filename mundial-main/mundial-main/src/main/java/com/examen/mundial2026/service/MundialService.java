package com.examen.mundial2026.service;

import com.examen.mundial2026.model.Grupo;
import com.examen.mundial2026.model.Jugador;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MundialService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${APP_DATA_PATH}")
    private String dataPath;

    public List<Jugador> getJugadores() throws IOException {
        return objectMapper.readValue(
                new File(dataPath + "/jugadores.json"),
                new TypeReference<List<Jugador>>() {});
    }

    public List<Jugador> getJugadoresPorPais(String pais) throws IOException {
        return getJugadores().stream()
                .filter(j -> j.getPais().equalsIgnoreCase(pais))
                .collect(Collectors.toList());
    }

    public List<Grupo> getGrupos() throws IOException {
        return objectMapper.readValue(
                new File(dataPath + "/grupos.json"),
                new TypeReference<List<Grupo>>() {});
    }

    public Map<String, Object> simularMundial() {
        Random rnd = new Random();

        List<String> equipos = new ArrayList<>(Arrays.asList(
            "España",       "Marruecos",
            "Francia",      "Senegal",
            "Argentina",    "Australia",
            "Brasil",       "Japón",
            "Alemania",     "Estados Unidos",
            "Portugal",     "México",
            "Inglaterra",   "Colombia",
            "Países Bajos", "Uruguay"
        ));

        List<Map<String, String>> octavos = new ArrayList<>();
        List<String> pasanOctavos = jugarFase(equipos, octavos, rnd);

        List<Map<String, String>> cuartos = new ArrayList<>();
        List<String> pasanCuartos = jugarFase(pasanOctavos, cuartos, rnd);

        List<Map<String, String>> semifinales = new ArrayList<>();
        List<String> pasanSemi = jugarFase(pasanCuartos, semifinales, rnd);

        String finalistaA = pasanSemi.contains("España") ? "España" : pasanSemi.get(0);
        String finalistaB = pasanSemi.stream().filter(e -> !e.equals(finalistaA)).findFirst().orElse("Francia");
        Map<String, String> finalPartido = crearPartido(finalistaA, finalistaB, "España", rnd);

        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("octavos", octavos);
        resultado.put("cuartos", cuartos);
        resultado.put("semifinales", semifinales);
        resultado.put("final", finalPartido);
        resultado.put("campeon", "🏆 España");
        return resultado;
    }

    private List<String> jugarFase(List<String> equipos, List<Map<String, String>> partidos, Random rnd) {
        List<String> ganadores = new ArrayList<>();
        for (int i = 0; i < equipos.size() - 1; i += 2) {
            String a = equipos.get(i);
            String b = equipos.get(i + 1);
            String ganador = (a.equals("España") || b.equals("España")) ? "España"
                    : (rnd.nextBoolean() ? a : b);
            partidos.add(crearPartido(a, b, ganador, rnd));
            ganadores.add(ganador);
        }
        return ganadores;
    }

    private Map<String, String> crearPartido(String a, String b, String ganador, Random rnd) {
        int ga, gb;
        if (ganador.equals(a)) {
            ga = rnd.nextInt(3) + 1;
            gb = rnd.nextInt(ga);
        } else {
            gb = rnd.nextInt(3) + 1;
            ga = rnd.nextInt(gb);
        }
        Map<String, String> p = new LinkedHashMap<>();
        p.put("local", a);
        p.put("visitante", b);
        p.put("resultado", ga + " - " + gb);
        p.put("ganador", ganador);
        return p;
    }
}
