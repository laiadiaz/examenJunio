package com.examen.mundial2026.model;

public class Partido {

    private String local;
    private String visitante;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String resultado;
    private String fecha;
    private String estadio;

    public Partido() {}

    public Partido(String local, String visitante, Integer golesLocal,
                   Integer golesVisitante, String resultado, String fecha, String estadio) {
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.resultado = resultado;
        this.fecha = fecha;
        this.estadio = estadio;
    }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getVisitante() { return visitante; }
    public void setVisitante(String visitante) { this.visitante = visitante; }

    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }

    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }
}
