package com.example.practica1p.dto;

import java.io.Serializable;

public class PokemonResponseDTO implements Serializable {

    private static final long serialVersionUID = -1933811219L;

    private Long idPokemon;
    private String nombrePokemon;
    private Integer altura;
    private Integer peso;
    private String tipos;
    private String rutaImagen;

    public Long getIdPokemon() {
        return this.idPokemon;
    }

    public void setIdPokemon(Long idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNombrePokemon() {
        return this.nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public Integer getAltura() {
        return this.altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPeso() {
        return this.peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getTipos() {
        return this.tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }

    public String getRutaImagen() {
        return this.rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    

}
