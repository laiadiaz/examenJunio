package com.examen.mundial2026.model;

import java.util.List;

public class Grupo {

    private String nombre;
    private List<String> paises;

    public Grupo() {}

    public Grupo(String nombre, List<String> paises) {
        this.nombre = nombre;
        this.paises = paises;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<String> getPaises() { return paises; }
    public void setPaises(List<String> paises) { this.paises = paises; }
}
