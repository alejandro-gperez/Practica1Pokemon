package com.example.practica1p.dto;

import java.io.Serializable;
import java.util.List;

public class PokemonResponseDTO implements Serializable {

    private static final long serialVersionUID = -1933811219L;

    private Long id;
    private String nombre;
    private Integer altura;
    private Integer peso;
    private List<String> tipos;
    private String imagen;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<String> getTipos() {
        return this.tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    

}
