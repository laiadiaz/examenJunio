package com.examen.mundial2026;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "APP_DATA_PATH=data")
class MundialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ── Página de inicio ──────────────────────────────────────

    @Test
    @DisplayName("La página de inicio devuelve 200 y contiene 'Mundial'")
    void inicio_devuelve200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mundial")));
    }

    // ── API Jugadores ─────────────────────────────────────────

    @Test
    @DisplayName("GET /api/jugadores devuelve 200 y un array no vacío")
    void jugadores_devuelveListaNoVacia() throws Exception {
        mockMvc.perform(get("/api/jugadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    @DisplayName("GET /api/jugadores contiene jugadores de España")
    void jugadores_contieneEspana() throws Exception {
        mockMvc.perform(get("/api/jugadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.pais == 'España')]").isArray())
                .andExpect(jsonPath("$[?(@.pais == 'España')]", hasSize(greaterThan(0))));
    }

    // ── API Grupos ────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/grupos devuelve exactamente 12 grupos")
    void grupos_devuelve12Grupos() throws Exception {
        mockMvc.perform(get("/api/grupos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(12));
    }

    @Test
    @DisplayName("GET /api/grupos contiene el Grupo H con España")
    void grupos_contieneGrupoH() throws Exception {
        mockMvc.perform(get("/api/grupos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.nombre == 'Grupo H')].paises[0]",
                        hasItem("España")));
    }

    // ── Página selección ──────────────────────────────────────

    @Test
    @DisplayName("GET /seleccion/España devuelve 200 y contiene jugadores conocidos")
    void seleccion_espana_devuelveJugadores() throws Exception {
        mockMvc.perform(get("/seleccion/España"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lamine Yamal")))
                .andExpect(content().string(containsString("Pedri")));
    }

    @Test
    @DisplayName("GET /seleccion con país desconocido muestra mensaje de lista vacía")
    void seleccion_paisDesconocido_listaVacia() throws Exception {
        mockMvc.perform(get("/seleccion/Narnia"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No hay jugadores registrados")));
    }

    // ── Página simulación ─────────────────────────────────────

    @Test
    @DisplayName("GET /simulacion devuelve 200 y España aparece como campeona")
    void simulacion_espanaCampeon() throws Exception {
        mockMvc.perform(get("/simulacion"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("España")));
    }

    @Test
    @DisplayName("GET /simulacion contiene las cuatro fases del torneo")
    void simulacion_contieneFases() throws Exception {
        mockMvc.perform(get("/simulacion"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Octavos")))
                .andExpect(content().string(containsString("Cuartos")))
                .andExpect(content().string(containsString("Semifinales")))
                .andExpect(content().string(containsString("Final")));
    }
}
